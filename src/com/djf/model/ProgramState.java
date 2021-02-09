package com.djf.model;

import com.djf.exceptions.EmptyStackException;
import com.djf.model.adt.*;
import com.djf.model.statement.IStatement;
import com.djf.model.value.StringValue;
import com.djf.model.value.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    private final MyIStack<IStatement> executionStack;
    private final MyIDictionary<String, Value> symbolTable;
    private final MyIList<Value> outList;
    private final MyIDictionary<StringValue, BufferedReader> fileTable;
    private final MyIHeapDictionary<Integer, Value> heapTable;
    private final IStatement originalProgram;
    private static final AtomicInteger staticThreadId = new AtomicInteger(0);
    private final int threadId;


    private final MyIBarrierDictionary<Integer, Pair<Integer, List<Integer>>> barrierTable;


    public synchronized int getId(){
        return threadId;
    }
    public static void incrementThreadId(){
        staticThreadId.incrementAndGet();
    }

    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, Value> symbolTable, MyIList<Value> outList, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeapDictionary<Integer, Value> heapTable, IStatement originalProgram,
                        MyIBarrierDictionary<Integer, Pair<Integer, List<Integer>>> barrierTable) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outList = outList;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.originalProgram = originalProgram;//TODO deep copy

        this.barrierTable = barrierTable;

        this.threadId = staticThreadId.get();
        incrementThreadId();

        executionStack.push(originalProgram);
    }

    public ProgramState(IStatement originalProgram) {
        this(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeapDictionary<>(), originalProgram,
                new MyBarrierDictionary<>());
    }

    public MyIBarrierDictionary<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        return barrierTable;
    }

    public MyIStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public MyIList<Value> getOutList() {
        return outList;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeapDictionary<Integer, Value> getHeapTable() {
        return heapTable;
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStepExecution() {
        if (executionStack.isEmpty())
            throw new EmptyStackException("Program stack is empty");

        IStatement crtStmt = executionStack.pop();
        return crtStmt.execute(this);
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("program id: ").append(getId()).append("\n");
        output.append("ExeStack:\n");
        for (var el : executionStack) {
            output.append(el).append("\n");
        }

        output.append("Symbol Table -").append(getId()).append(":\n");
        for (var el : symbolTable) {
            output.append(el.getKey()).append(" -> ").append(el.getValue()).append("\n");
        }

        output.append("Out:\n");
        for (var el : outList) {
            output.append(el).append("\n");
        }

        output.append("File Table:\n");
        for (var el : fileTable) {
            output.append(el.getKey()).append("\n");
        }

        output.append("Heap Table:\n");
        for (var el : heapTable) {
            output.append(el.getKey()).append(" -> ").append(el.getValue()).append("\n");
        }

        return output.toString();
    }
}
