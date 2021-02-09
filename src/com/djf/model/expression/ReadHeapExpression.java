package com.djf.model.expression;

import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.type.RefType;
import com.djf.model.type.Type;
import com.djf.model.value.RefValue;
import com.djf.model.value.Value;

public class ReadHeapExpression implements IExpression{

    private final IExpression expression;

    public ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }


    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Integer, Value> heapTable) {
        Value expressionValue = expression.evaluate(symbolTable, heapTable);
        if(!(expressionValue.getType() instanceof RefType))
            throw new RuntimeException("The expression: " + expressionValue + " must be of RefType");

        RefValue exprRefValue = (RefValue) expressionValue;

        if(!heapTable.containsKey(exprRefValue.getAddress()))
            throw new RuntimeException("Cannot access address " + exprRefValue.getAddress());

        return heapTable.get(exprRefValue.getAddress());

    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        var typ = expression.typeCheck(typeEnv);
        if(!(typ instanceof RefType))
            throw new RuntimeException("The rH argument is not a Ref Type");

        RefType rTyp = (RefType) typ;
        return rTyp.getInner();
    }


    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }
}
