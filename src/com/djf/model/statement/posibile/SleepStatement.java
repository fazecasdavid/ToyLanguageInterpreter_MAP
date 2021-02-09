package com.djf.model.statement.posibile;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.expression.ValueExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.type.IntType;
import com.djf.model.type.Type;
import com.djf.model.value.IntValue;

public class SleepStatement implements IStatement {
    private final IExpression exp;

    public SleepStatement(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) {

        int primitiveValue = ((IntValue) exp.evaluate(state.getSymbolTable(), state.getHeapTable())).getValue();

        if (primitiveValue <= 0)
            return null;

        state.getExecutionStack().push(new SleepStatement(new ValueExpression(new IntValue(primitiveValue - 1))));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        if (!exp.typeCheck(typeEnv).equals(new IntType()))
            throw new RuntimeException("Expression from sleep must be of type int" + exp);

        return typeEnv;
    }

    @Override
    public String toString() {
        return "Sleep(" + exp + ")";
    }
}
