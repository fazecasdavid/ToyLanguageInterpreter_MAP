package com.djf.model.expression;

import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.type.Type;
import com.djf.model.value.Value;

public class ValueExpression implements IExpression {
    private final Value value;


    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Integer, Value> heapTable) {
        return value;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        return value.getType();
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
