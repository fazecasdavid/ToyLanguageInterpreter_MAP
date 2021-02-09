package com.djf.repository;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.adt.MyIList;
import com.djf.model.value.StringValue;
import com.djf.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public interface IRepository {
    void addProgramState(ProgramState programState);

    //    ProgramState getCurrentProgramState();
    //    void setProgramState(int index);
    void logPrgStateExec(ProgramState programState);

    String getLogFilePath();

    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> programStates);

    MyIHeapDictionary<Integer, Value> getProgramHeap();

    MyIList<Value> getProgramOutList();

    MyIDictionary<StringValue, BufferedReader> getProgramFileTable();

    void setLogFilePath(String logFilePath);

}
