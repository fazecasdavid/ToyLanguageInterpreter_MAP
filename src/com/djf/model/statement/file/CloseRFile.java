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
import java.io.IOException;

public class CloseRFile implements IStatement {
    private final IExpression expression;

    public CloseRFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymbolTable();
        var fileTable = state.getFileTable();

        Value fileNameValue = expression.evaluate(symbolTable, state.getHeapTable());
        if(!fileNameValue.getType().equals(new StringType()))
            throw new RuntimeException("File name must be of type String");

        // safe to downcast
        StringValue fileNameStringValue = (StringValue)fileNameValue;

        if(!fileTable.containsKey(fileNameStringValue))
            throw new RuntimeException("The file is not opened");

        BufferedReader reader = null;
        try{
            reader = fileTable.get(fileNameStringValue);
            reader.close();
        }
        catch (IOException ex){
            throw new RuntimeException("Something went wrong closing " + fileNameStringValue);
        }

        fileTable.remove(fileNameStringValue);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        var typeExp = expression.typeCheck(typeEnv);
        if(!typeExp.equals(new StringType()))
            throw new RuntimeException("CloseRFile expression must be of type string");
        return typeEnv;
    }


    @Override
    public String toString() {
        return "CloseRFile(" + expression + ")";
    }
}
