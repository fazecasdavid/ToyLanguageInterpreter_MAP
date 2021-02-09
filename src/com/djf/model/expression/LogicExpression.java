package com.djf.model.expression;

import com.djf.exceptions.DataTypesNotMatchingException;
import com.djf.exceptions.InvalidOperatorException;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.type.BoolType;
import com.djf.model.type.IntType;
import com.djf.model.type.Type;
import com.djf.model.value.BoolValue;
import com.djf.model.value.Value;

public class LogicExpression implements IExpression {
    private final IExpression leftExpression;
    private final IExpression rightExpression;
    private final char operator;

    public LogicExpression(IExpression leftExpression, IExpression rightExpression, char operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Integer, Value> heapTable) {
        Value leftValue = leftExpression.evaluate(symbolTable, heapTable);
        Value rightValue = rightExpression.evaluate(symbolTable, heapTable);

        if (!leftValue.getType().equals(new BoolType()))
            throw new DataTypesNotMatchingException();
        if (!rightValue.getType().equals(new BoolType()))
            throw new DataTypesNotMatchingException();

        boolean leftPrimitiveValue = ((BoolValue) leftValue).getValue();
        boolean rightPrimitiveValue = ((BoolValue) rightValue).getValue();

        if (operator == '|')
            return new BoolValue(leftPrimitiveValue || rightPrimitiveValue);
        if (operator == '&')
            return new BoolValue(leftPrimitiveValue && rightPrimitiveValue);

        throw new InvalidOperatorException();

    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        var typeLeft = leftExpression.typeCheck(typeEnv);
        var typeRight = rightExpression.typeCheck(typeEnv);

        if(!typeLeft.equals(new BoolType()))
            throw new RuntimeException("First operand: " + leftExpression + " is not boolean");
        if(!typeRight.equals(new BoolType()))
            throw new RuntimeException("Second operand: " + rightExpression + " is not boolean");

        return new BoolType();
    }


    public String toString() {
        return leftExpression.toString() + operator + rightExpression.toString();
    }
}
