package com.djf.model.expression;

import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.type.Type;
import com.djf.model.value.Value;

public interface IExpression {
    Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Integer, Value> heapTable);

    Type typeCheck(MyIDictionary<String,Type> typeEnv);
}
