package com.djf.model.statement;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyDictionary;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyStack;
import com.djf.model.type.Type;
import com.djf.model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class ForkStatement implements IStatement{

    private final IStatement innerStatement;

    public ForkStatement(IStatement innerStatement) {
        this.innerStatement = innerStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> shallowCopySymTable = new MyDictionary<>(state.getSymbolTable());


        return new ProgramState(new MyStack<>(),
                shallowCopySymTable,
                state.getOutList(),
                state.getFileTable(),
                state.getHeapTable(),
                innerStatement,
                state.getBarrierTable());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        innerStatement.typeCheck(new MyDictionary<>(typeEnv));
        return typeEnv;
    }


    @Override
    public String toString() {
        return "fork( " + innerStatement + " )";
    }
}
