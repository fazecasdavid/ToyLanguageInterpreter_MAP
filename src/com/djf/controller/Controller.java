package com.djf.controller;

import com.djf.exceptions.EmptyStackException;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.adt.MyIList;
import com.djf.model.adt.MyIStack;
import com.djf.model.statement.IStatement;
import com.djf.model.type.RefType;
import com.djf.model.value.RefValue;
import com.djf.model.value.StringValue;
import com.djf.model.value.Value;
import com.djf.repository.IRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
//        this.executor = Executors.newFixedThreadPool(3);
    }


    public void allSteps() {
        this.executor = Executors.newFixedThreadPool(2);
        var prgList = removeCompletedPrg(repository.getProgramList());
        while (prgList.size() > 0) {
            var programHeap = repository.getProgramHeap();

            // conservative garbage collector
            programHeap.setContent(
                    conservativeGarbageCollector(getAllReferencedAddresses(
                            getAddressesFromAllSymTables(), programHeap.getContent()),
                            programHeap.getContent())
            );

            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(prgList);
        }
        executor.shutdownNow();
        executor = null;
        // HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository

        // update the repository state
//        assert(prgList.isEmpty());
        repository.setProgramList(prgList);

    }

    public void oneStepForAllConcurrent() {
        if(this.executor == null)
            this.executor = Executors.newFixedThreadPool(2);
        var prgList = removeCompletedPrg(repository.getProgramList());
        if (prgList.size() > 0) {
            var programHeap = repository.getProgramHeap();
            programHeap.setContent(
                    conservativeGarbageCollector(getAllReferencedAddresses(
                            getAddressesFromAllSymTables(), programHeap.getContent()),
                            programHeap.getContent())
            );

            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(prgList);
        }
        if(prgList.isEmpty()){
            executor.shutdownNow();
            executor = null;
        }
        repository.setProgramList(prgList);
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) {
        // before the execution, print the PrgState List into the log file
        prgList.forEach(repository::logPrgStateExec);

        // prepare the list of callables
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStepExecution))
                .collect(Collectors.toList());

        // start the execution of the callables
        // it returns the list of new created PrgStates (namely threads)
        List<ProgramState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            // System.out.println(e.getMessage());
                            throw new RuntimeException(e.getMessage());
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        // add the new created threads to the list of existing threads
        prgList.addAll(newPrgList); // performs null safe check

        //after the execution, print the PrgState List into the log file
        prgList.forEach(repository::logPrgStateExec);
        //Save the current programs in the repository
        repository.setProgramList(prgList);
    }


    List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }


    public List<ProgramState> getAllProgramStates() {
        return repository.getProgramList();
    }


//    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, Value> heapTable) {
//        return heapTable.entrySet().stream()
//                .filter(e -> symTableAddresses.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

//    private Map<Integer, Value> safeGarbageCollector(List<Integer> referencedAddresses, Map<Integer, Value> heapTable) {
//        return heapTable.entrySet().stream()
//                .filter(e -> referencedAddresses.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

    private Map<Integer, Value> conservativeGarbageCollector(List<Integer> referencedAddresses, Map<Integer, Value> heapTable) {
        return heapTable.entrySet().stream()
                .filter(e -> referencedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAllReferencedAddresses(List<Integer> allSymTablesAddresses, Map<Integer, Value> heapTable) {
        var allReferencedAddresses = new ArrayList<>(allSymTablesAddresses);

        for (var adr : allSymTablesAddresses) {
            var v = heapTable.get(adr);
            while (v instanceof RefValue) {
                int address = ((RefValue) v).getAddress();
                if (allReferencedAddresses.contains(address)) // to avoid cyclic referencing
                    break;
                allReferencedAddresses.add(address);
                v = heapTable.get(address);
            }
        }

        return allReferencedAddresses;
    }

    private List<Integer> getAddressesFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v.getType() instanceof RefType)
                .map(v -> ((RefValue) v).getAddress())
//                .filter(v -> v!= 0)
                .collect(Collectors.toList());
    }

    private List<Integer> getAddressesFromAllSymTables() {
        var allValues = repository.getProgramList().stream()
                .map(p -> p.getSymbolTable().getContent().values())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return getAddressesFromSymTable(allValues);
    }

    public MyIHeapDictionary<Integer, Value> getProgramHeap() {
        return repository.getProgramHeap();
    }

    public MyIList<Value> getProgramOutList() {
        return repository.getProgramOutList();
    }

    public MyIDictionary<StringValue, BufferedReader> getProgramFileTable() {
        return repository.getProgramFileTable();
    }

    public List<ProgramState> getProgramStates() {
        return repository.getProgramList();
    }

}
