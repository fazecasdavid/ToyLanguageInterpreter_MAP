package com.djf.model.statement.barrier;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.type.IntType;
import com.djf.model.type.Type;
import com.djf.model.value.IntValue;
import javafx.util.Pair;

import java.util.ArrayList;

public class NewBarrierStatement implements IStatement {
    private final String variableId;
    private final IExpression expression;

    public NewBarrierStatement(String variableId, IExpression expression) {
        this.variableId = variableId;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {

//        synchronize on a shared state
        synchronized (state.getBarrierTable()) {
            var symbolTable = state.getSymbolTable();
            var heapTable = state.getHeapTable();
            var barrierTable = state.getBarrierTable();

            if(!expression.evaluate(symbolTable, heapTable).getType().equals(new IntType()))
                throw new RuntimeException("Expression from NewBarrier: " + expression + " must be of type int");

            if(!symbolTable.get(variableId).getType().equals(new IntType()))
                throw new RuntimeException("Variable from NewBarrier: " + variableId + " must be of type int");


            var Nr = ((IntValue)expression.evaluate(symbolTable, heapTable)).getValue();
            var newFreeLocation = barrierTable.getNextFreeLocation();

            barrierTable.put(newFreeLocation, new Pair<>(Nr, new ArrayList<>()));
            symbolTable.put(variableId, new IntValue(newFreeLocation));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        if (!expression.typeCheck(typeEnv).equals(new IntType()))
            throw new RuntimeException("Expression from NewBarrier: " + expression + " must be of type int");

        if (!typeEnv.containsKey(variableId))
            throw new RuntimeException(variableId + " is not declared");

        if (!typeEnv.get(variableId).equals(new IntType()))
            throw new RuntimeException(variableId + " must be of type int");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "NewBarrier(" + variableId + ", " + expression + ")";
    }
}
