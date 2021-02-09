package com.djf.model.statement.heap;

import com.djf.exceptions.MyException;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.type.RefType;
import com.djf.model.type.Type;
import com.djf.model.value.RefValue;
import com.djf.model.value.Value;


public class WriteHeapStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public WriteHeapStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymbolTable();
        var heapTable = state.getHeapTable();

        // checks for variableName
        if (!symbolTable.containsKey(variableName))
            throw new RuntimeException(variableName + " not declared");
        Value variableValue = symbolTable.get(variableName);
        if (!(variableValue.getType() instanceof RefType))
            throw new RuntimeException(variableName + " is not of RefType");
        RefValue variableRefValue = (RefValue) variableValue;
        if (!heapTable.containsKey(variableRefValue.getAddress()))
            throw new RuntimeException(variableName + " address is not in heapTable");


        // checks for expression
        Value expressionValue = expression.evaluate(symbolTable, heapTable);
        if (!expressionValue.getType().equals(variableRefValue.getLocationType()))
            throw new RuntimeException("Types: " + expressionValue.getType() + ", " +
                    variableRefValue.getLocationType() + " do not match");


        heapTable.put(variableRefValue.getAddress(), expressionValue);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        var varType = typeEnv.get(variableName);
        var expType = expression.typeCheck(typeEnv);
        if(!varType.equals(new RefType(expType)))
            throw new MyException("wH stmt: right hand side and left hand side have different types");

        return typeEnv;

    }


    @Override
    public String toString() {
        return "wH(" + variableName + ", " + expression + ")";
    }
}
