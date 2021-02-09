package com.djf.model.expression;

import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.type.BoolType;
import com.djf.model.type.IntType;
import com.djf.model.type.Type;
import com.djf.model.value.BoolValue;
import com.djf.model.value.IntValue;
import com.djf.model.value.Value;

public class RelationalExpression implements IExpression {
    private final IExpression leftExpression;
    private final IExpression rightExpression;
    private final String operator;

    public RelationalExpression(IExpression leftExpression, IExpression rightExpression, String operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Integer, Value> heapTable) {
        Value leftValue = leftExpression.evaluate(symbolTable, heapTable);
        Value rightValue = rightExpression.evaluate(symbolTable, heapTable);


        if (!leftValue.getType().equals(new IntType()))
            throw new RuntimeException(leftExpression.toString() + " must be of type int");
        if (!rightValue.getType().equals(new IntType()))
            throw new RuntimeException(rightExpression.toString() + " must be of type int");

        int leftPrimitiveValue = ((IntValue) leftValue).getValue();
        int rightPrimitiveValue = ((IntValue) rightValue).getValue();

        switch (operator) {
            case "<" -> {
                return new BoolValue(leftPrimitiveValue < rightPrimitiveValue);
            }
            case "<=" -> {
                return new BoolValue(leftPrimitiveValue <= rightPrimitiveValue);
            }
            case "==" -> {
                return new BoolValue(leftPrimitiveValue == rightPrimitiveValue);
            }
            case "!=" -> {
                return new BoolValue(leftPrimitiveValue != rightPrimitiveValue);
            }
            case ">" -> {
                return new BoolValue(leftPrimitiveValue > rightPrimitiveValue);
            }
            case ">=" -> {
                return new BoolValue(leftPrimitiveValue >= rightPrimitiveValue);
            }
        }
        throw new RuntimeException(operator + " is not a valid operator");

    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        var typeLeft = leftExpression.typeCheck(typeEnv);
        var typeRight = rightExpression.typeCheck(typeEnv);

        if(!typeLeft.equals(new IntType()))
            throw new RuntimeException("First operand: " + leftExpression + " is not an integer");
        if(!typeRight.equals(new IntType()))
            throw new RuntimeException("Second operand: " + rightExpression + " is not an integer");

        return new BoolType();
    }


    public String toString() {
        return leftExpression.toString() + operator + rightExpression.toString();
    }
}
