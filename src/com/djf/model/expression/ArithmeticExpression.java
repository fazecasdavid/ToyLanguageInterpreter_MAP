package com.djf.model.expression;

import com.djf.exceptions.DataTypesNotMatchingException;
import com.djf.exceptions.DivisionByZeroException;
import com.djf.exceptions.InvalidOperatorException;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.adt.MyIHeapDictionary;
import com.djf.model.type.BoolType;
import com.djf.model.type.IntType;
import com.djf.model.type.Type;
import com.djf.model.value.IntValue;
import com.djf.model.value.Value;

public class ArithmeticExpression implements IExpression {
    private final IExpression leftExpression;
    private final IExpression rightExpression;
    private final char operator;


    public ArithmeticExpression(char operator, IExpression leftExpression, IExpression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Integer, Value> heapTable) {
        Value leftValue = leftExpression.evaluate(symbolTable, heapTable);
        Value rightValue = rightExpression.evaluate(symbolTable, heapTable);

        if (!leftValue.getType().equals(new IntType()))
            throw new DataTypesNotMatchingException();
        if (!rightValue.getType().equals(new IntType()))
            throw new DataTypesNotMatchingException();

        int leftPrimitiveValue = ((IntValue) leftValue).getValue();
        int rightPrimitiveValue = ((IntValue) rightValue).getValue();

        if (operator == '+')
            return new IntValue(leftPrimitiveValue + rightPrimitiveValue);
        if (operator == '-')
            return new IntValue(leftPrimitiveValue - rightPrimitiveValue);
        if (operator == '*')
            return new IntValue(leftPrimitiveValue * rightPrimitiveValue);
        if (operator == '/') {
            if (rightPrimitiveValue == 0)
                throw new DivisionByZeroException();
            return new IntValue(leftPrimitiveValue / rightPrimitiveValue);
        }

        throw new InvalidOperatorException();
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        var typeLeft = leftExpression.typeCheck(typeEnv);
        var typeRight = rightExpression.typeCheck(typeEnv);

        if(!typeLeft.equals(new IntType()))
            throw new RuntimeException("First operand: " + leftExpression + " is not an integer");
        if(!typeRight.equals(new IntType()))
            throw new RuntimeException("Second operand: " + rightExpression + " is not an integer");

        return new IntType();
    }


    @Override
    public String toString() {
        return leftExpression.toString() + operator + rightExpression.toString();
    }
}
