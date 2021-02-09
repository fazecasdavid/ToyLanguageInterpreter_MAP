package com.djf.model.statement.barrier;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.statement.IStatement;
import com.djf.model.type.IntType;
import com.djf.model.type.Type;
import com.djf.model.value.IntValue;
import javafx.util.Pair;

import java.util.ArrayList;

public class AwaitStatement implements IStatement {
    private final String variableId;

    public AwaitStatement(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState state) {

        //        synchronize on a shared state
        synchronized (state.getBarrierTable()) {
            var symbolTable = state.getSymbolTable();
            var heapTable = state.getHeapTable();
            var barrierTable = state.getBarrierTable();


            if(!symbolTable.get(variableId).getType().equals(new IntType()))
                throw new RuntimeException("Variable from Await: " + variableId + " must be of type int");

            // the typeChecker assures us that variableId is of type Int
            var foundIndex = ((IntValue)symbolTable.get(variableId)).getValue();

            if(!barrierTable.containsKey(foundIndex))
                throw new RuntimeException(variableId + " has no link in the BarrierTable at: " + foundIndex);

            var foundEntry = barrierTable.get(foundIndex);
            var N1 = foundEntry.getKey();
            var List1 = foundEntry.getValue();
            var NL = List1.size();
            var id = state.getId();

            if(N1 > NL) {
                if (!List1.contains(id)) {
                    List1.add(id);
                }
                state.getExecutionStack().push(this);
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        if (!typeEnv.containsKey(variableId))
            throw new RuntimeException(variableId + " is not declared");

        if (!typeEnv.get(variableId).equals(new IntType()))
            throw new RuntimeException(variableId + " must be of type int");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "Await(" + variableId + ")";
    }
}
