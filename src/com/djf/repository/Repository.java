package com.djf.repository;

import com.djf.exceptions.ListOutOfRangeException;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.adt.MyIList;
import com.djf.model.value.StringValue;
import com.djf.model.value.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private String logFilePath;
    private List<ProgramState> programStates;
//    private int currentProgramStateIndex;

    public Repository(ProgramState programState, String logFilePath) {
        this.programStates = new ArrayList<>();
        this.programStates.add(programState);
//        this.currentProgramStateIndex = 0;

        this.logFilePath = logFilePath;
    }

//    public Repository(List<ProgramState> programStates, int currentProgramStateIndex) {
//        this.programStates = programStates;
//        this.currentProgramStateIndex = currentProgramStateIndex;
//        this.logFilePath = "";
//    }
//    public Repository() {
//        this.programStates = new ArrayList<>();
//        this.currentProgramStateIndex = -1;
//        this.logFilePath = "";
//    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public void addProgramState(ProgramState programState) {
        programStates.add(programState);
    }

//    @Override
//    public ProgramState getCurrentProgramState() {
//        if(currentProgramStateIndex < 0 || currentProgramStateIndex > programStates.size())
//            throw new ListOutOfRangeException();
//        return programStates.get(currentProgramStateIndex);
//    }

//    @Override
//    public void setProgramState(int index) {
//        if(index < 0 || index > programStates.size())
//            throw new ListOutOfRangeException();
//
//        currentProgramStateIndex = index;
//    }

    @Override
    public void logPrgStateExec(ProgramState programState) {
        if (logFilePath.isEmpty()) {
            throw new RuntimeException("You did not set a logfile path");
        }
        try {
            var logFileWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFileWriter.println(programState + "\n");
            logFileWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ProgramState> getProgramList() {
        return programStates;
    }

    public void setProgramList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public MyIHeapDictionary<Integer, Value> getProgramHeap() {
        return programStates.get(0).getHeapTable();
    }

    @Override
    public MyIList<Value> getProgramOutList() {
        return programStates.get(0).getOutList();
    }

    @Override
    public MyIDictionary<StringValue, BufferedReader> getProgramFileTable() {
        return programStates.get(0).getFileTable();
    }


}
