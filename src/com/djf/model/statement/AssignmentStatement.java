package com.djf.model.statement;

import com.djf.exceptions.DataTypesNotMatchingException;
import com.djf.exceptions.NotDeclaredVariableException;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.type.Type;
import com.djf.model.value.Value;

public class AssignmentStatement implements IStatement {

    private final String variableId;    // variable name
    private final IExpression expression;

    public AssignmentStatement(String variableId, IExpression expression) {
        this.variableId = variableId;
        this.expression = expression;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        if (symbolTable.containsKey(variableId)) {
            Value value = expression.evaluate(symbolTable, state.getHeapTable());
            Type type = symbolTable.get(variableId).getType();
            if (value.getType().equals(type))
                symbolTable.put(variableId, value);
            else
                throw new DataTypesNotMatchingException("Declared type of variable" + variableId + " and type of" +
                        "the assigned expression do not match");
        } else
            throw new NotDeclaredVariableException("The used variable " + variableId + " was not declared");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        var variableType = typeEnv.get(variableId);
        var expressionType = expression.typeCheck(typeEnv);

        if(!variableType.equals(expressionType))
            throw new RuntimeException("Assignment: right hand side and left hand side have different types");

        return typeEnv;
    }


    @Override
    public String toString() {
        return variableId + "=" + expression.toString();
    }
}
