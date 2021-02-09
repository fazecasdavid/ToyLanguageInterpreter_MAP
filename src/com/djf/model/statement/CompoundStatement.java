package com.djf.model.statement;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIStack;
import com.djf.model.type.Type;

public class CompoundStatement implements IStatement {
    private final IStatement first;
    private final IStatement snd;

    public CompoundStatement(IStatement first, IStatement snd) {
        this.first = first;
        this.snd = snd;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<IStatement> stk = state.getExecutionStack();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
//        var typeEnv1 = first.typeCheck(typeEnv);
//        var typeEnv2 = snd.typeCheck(typeEnv1);
//        return typeEnv2;

        return snd.typeCheck(first.typeCheck(typeEnv));
    }


    @Override
    public String toString() {
        return first.toString() + ";    " + snd.toString();
    }
}
