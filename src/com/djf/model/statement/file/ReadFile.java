package com.djf.model.statement.file;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.type.IntType;
import com.djf.model.type.StringType;
import com.djf.model.type.Type;
import com.djf.model.value.IntValue;
import com.djf.model.value.StringValue;
import com.djf.model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private final IExpression expression;
    private final String variableName;

    public ReadFile(IExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymbolTable();
        var fileTable = state.getFileTable();
        if(!symbolTable.containsKey(variableName))
            throw new RuntimeException("Variable " + variableName + " is not declared");

        Value variableValue = symbolTable.get(variableName);

        if(!variableValue.getType().equals(new IntType()))
            throw new RuntimeException("Variable " + variableName + " must be of type INT");

        Value fileNameValue = expression.evaluate(symbolTable, state.getHeapTable());

        if(!fileNameValue.getType().equals(new StringType()))
            throw new RuntimeException("The file name must be of type String");

        // It is safe to downcast now
        StringValue fileNameStringValue = (StringValue)fileNameValue;

        if(!fileTable.containsKey(fileNameStringValue))
            throw new RuntimeException("The file " + fileNameStringValue + " is not opened");

        BufferedReader bufferedReader = null;
        String line = null;
        try{
            bufferedReader = fileTable.get(fileNameStringValue);
            line = bufferedReader.readLine();
        }
        catch (IOException ex){
            throw new RuntimeException("Something went wrong reading from file " + fileNameStringValue);
        }

        if(line == null){
            variableValue = new IntType().getDefaultValue();
        }
        else{
            variableValue = new IntValue(Integer.parseInt(line));
        }

        symbolTable.put(variableName, variableValue);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        typeEnv.get(variableName);
        var typeExp = expression.typeCheck(typeEnv);

        if(!typeExp.equals(new StringType()))
            throw new RuntimeException("ReadFile: first argument expression must be of type String");
        return typeEnv;
    }


    @Override
    public String toString() {
        return "ReadFile(" + expression + ", " + variableName + ")";
    }
}
