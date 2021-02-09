package com.djf.model.statement.heap;

import com.djf.exceptions.MyException;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.type.RefType;
import com.djf.model.type.Type;
import com.djf.model.value.RefValue;


public class NewStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public NewStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymbolTable();
        var heapTable = state.getHeapTable();

        // check if variableName is declared
        if (!symbolTable.containsKey(variableName))
            throw new RuntimeException("Variable " + variableName + " not declared");

        // variable must be of type Ref
        if (!(symbolTable.get(variableName).getType() instanceof RefType))
            throw new RuntimeException("Variable " + variableName + " must be of type RefType");

        var expressionValue = expression.evaluate(symbolTable, state.getHeapTable());
        var refVariable = (RefValue) symbolTable.get(variableName);

        // expression and variable must have matching types
        if (!refVariable.getLocationType().equals(expressionValue.getType()))
            throw new RuntimeException("Expression " + variableName + " and variable type " + expressionValue.getType() + " do not match");


        var freeLocation = heapTable.getNextFreeLocation();
        heapTable.put(freeLocation, expressionValue);

        // update symbol table address
        var value = (RefValue) symbolTable.get(variableName);
        var updatedValue = new RefValue(freeLocation, value.getLocationType());

        symbolTable.put(variableName, updatedValue);


        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        Type typeVar = typeEnv.get(variableName);
        Type typeExp = expression.typeCheck(typeEnv);
        if (!typeVar.equals(new RefType(typeExp)))
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");

        return typeEnv;
    }


    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression + ")";
    }

}
