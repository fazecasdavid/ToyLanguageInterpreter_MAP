package com.djf.model.statement;

import com.djf.exceptions.MyException;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.type.*;
import com.djf.model.value.*;

public class VariableDeclarationStatement implements IStatement {
    private final String name;
    private final Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        if(symbolTable.containsKey(name))
            throw new MyException("The variable is already declared");


//        if(type.equals(new IntType()))
//            symbolTable.put(name, new IntType().getDefaultValue());  // Init it with 0
//        else if(type.equals(new BoolType()))
//            symbolTable.put(name, new BoolType().getDefaultValue());
//        else if(type.equals(new StringType()))
//            symbolTable.put(name, new StringType().getDefaultValue());
//        else if(type instanceof RefType)
//            symbolTable.put(name, new RefType().getDefaultValue());

        symbolTable.put(name, type.getDefaultValue());

        return null;

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        typeEnv.put(name, type);
        return typeEnv;
    }


    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
