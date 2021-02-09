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

public class WhileStatement implements IStatement{
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        var symbolTable = state.getSymbolTable();
        var heapTable = state.getHeapTable();

        Value value = expression.evaluate(symbolTable, heapTable);
        if (!value.getType().equals(new BoolType()))
            throw new DataTypesNotMatchingException("Expression from While statement not boolean");
        // we can safely down cast to BoolType
        if (((BoolValue) value).getValue()){
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(statement);
        }
        return null;

    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        if(!expression.typeCheck(typeEnv).equals(new BoolType()))
            throw new RuntimeException("The condition of WHILE has not the type bool");

        statement.typeCheck(new MyDictionary<>(typeEnv));
        return typeEnv;
    }


    @Override
    public String toString() {
        return "while(" + expression + ") {" + statement + "}";
    }
}
