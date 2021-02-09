package com.djf.model.statement.file;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.type.StringType;
import com.djf.model.type.Type;
import com.djf.model.value.StringValue;
import com.djf.model.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenRFile implements IStatement {
    private final IExpression expression;

    public OpenRFile(IExpression expression) {
        this.expression = expression;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        var fileTable = state.getFileTable();
        Value expressionValue = expression.evaluate(state.getSymbolTable(), state.getHeapTable());
        if (!expressionValue.getType().equals(new StringType()))
            throw new RuntimeException("File name must be of type string");

        StringValue fileNameValue = (StringValue)expressionValue;
        
        if(fileTable.containsKey(fileNameValue))
            throw new RuntimeException("File " + fileNameValue.toString() + " is already opened!");

        BufferedReader openedFile = null;
        try{
            openedFile = new BufferedReader(new FileReader(fileNameValue.getValue()));
            fileTable.put(fileNameValue, openedFile);
        }
        catch (Exception ex){
            throw new RuntimeException("Something went wrong opening file: " + fileNameValue.toString());
        }

        return null;
    }
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        var typeExp = expression.typeCheck(typeEnv);
        if(!typeExp.equals(new StringType()))
            throw new RuntimeException("openRFile expression must be of type string");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }
}
