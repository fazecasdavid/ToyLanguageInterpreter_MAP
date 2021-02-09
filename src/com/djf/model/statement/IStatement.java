package com.djf.model.statement;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.type.Type;

public interface IStatement {
    ProgramState execute(ProgramState state);

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv);
}
