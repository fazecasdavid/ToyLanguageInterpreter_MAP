package com.djf.model.statement;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIList;
import com.djf.model.expression.IExpression;
import com.djf.model.type.Type;
import com.djf.model.value.Value;

public class PrintStatement implements IStatement {

    private final IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIList<Value> out = state.getOutList();
        out.addToEnd(expression.evaluate(state.getSymbolTable(), state.getHeapTable()));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }


    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
