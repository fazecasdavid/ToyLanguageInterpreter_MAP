package com.djf.model.statement;

import com.djf.exceptions.DataTypesNotMatchingException;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyDictionary;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.type.BoolType;
import com.djf.model.type.Type;
import com.djf.model.value.BoolValue;
import com.djf.model.value.Value;

public class IfStatement implements IStatement {
    private final IExpression expression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symTbl = state.getSymbolTable();

        Value value = expression.evaluate(symTbl, state.getHeapTable());
        if (!value.getType().equals(new BoolType()))
            throw new DataTypesNotMatchingException("Expression from IF statement not boolean");
        // we can safely down cast to BoolType
        if (((BoolValue) value).getValue())
//            thenStatement.execute(state);
            state.getExecutionStack().push(thenStatement);
        else
//            elseStatement.execute(state);
            state.getExecutionStack().push(elseStatement);
        return null;

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        if(!expression.typeCheck(typeEnv).equals(new BoolType()))
            throw new RuntimeException("The condition of IF has not the type bool");

        // clone typeEnv
        thenStatement.typeCheck(new MyDictionary<>(typeEnv.getContent()));
        elseStatement.typeCheck(new MyDictionary<>(typeEnv.getContent()));

        return typeEnv;
    }


    @Override
    public String toString() {
        return "IF(" + expression.toString() + ") THEN {" + thenStatement.toString() + "} ELSE {" + elseStatement.toString() + "}";
    }
}
