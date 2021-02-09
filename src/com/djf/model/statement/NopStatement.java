package com.djf.model.statement;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.type.Type;

public class NopStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        return typeEnv;
    }


    @Override
    public String toString() {
        return "NOP";
    }
}
