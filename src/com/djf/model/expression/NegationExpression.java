package com.djf.model.expression;

import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.type.BoolType;
import com.djf.model.type.Type;
import com.djf.model.value.BoolValue;
import com.djf.model.value.Value;

public class NegationExpression implements IExpression {
    private final IExpression expression;

    public NegationExpression(IExpression expression) {
        this.expression = expression;

    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Integer, Value> heapTable) {
        Value value = expression.evaluate(symbolTable, heapTable);

        boolean primitiveValue = ((BoolValue) value).getValue();

        return new BoolValue(!primitiveValue);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        var type = expression.typeCheck(typeEnv);

        if(!type.equals(new BoolType()))
            throw new RuntimeException("Expression:: " + expression + " is not boolean");

        return new BoolType();
    }


    public String toString() {
        return "!" + expression;
    }
}
