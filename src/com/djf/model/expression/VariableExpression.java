package com.djf.model.expression;

import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.type.Type;
import com.djf.model.value.Value;

public class VariableExpression implements IExpression {

    private final String variableId;    // variable name

    public VariableExpression(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Integer, Value> heapTable) {
        return symbolTable.get(variableId);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        return typeEnv.get(variableId);
    }


    @Override
    public String toString() {
        return variableId;
    }
}
