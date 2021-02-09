package com.djf.model.statement.posibile;

import com.djf.exceptions.NotDeclaredVariableException;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.statement.AssignmentStatement;
import com.djf.model.statement.IStatement;
import com.djf.model.statement.IfStatement;
import com.djf.model.type.BoolType;
import com.djf.model.type.Type;
import com.djf.model.value.BoolValue;

public class ConditionalAssignmentStatement implements IStatement {

    private final String variableId;
    private final IExpression exp1;
    private final IExpression exp2;
    private final IExpression exp3;

    public ConditionalAssignmentStatement(String variableId, IExpression exp1, IExpression exp2, IExpression exp3) {
        this.variableId = variableId;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }


    @Override
    public ProgramState execute(ProgramState state) {

        if (!state.getSymbolTable().containsKey(variableId))
            throw new NotDeclaredVariableException("The used variable " + variableId + " was not declared");

        var newIfStatement = new IfStatement(exp1, new AssignmentStatement(variableId, exp2), new AssignmentStatement(variableId, exp3));

        state.getExecutionStack().push(newIfStatement);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        var variableType = typeEnv.get(variableId);
        var exp1Type = exp1.typeCheck(typeEnv);
        var exp2Type = exp2.typeCheck(typeEnv);
        var exp3Type = exp3.typeCheck(typeEnv);

        if (!exp1Type.equals(new BoolType()))
            throw new RuntimeException("Expression from conditional assignment: " + exp1 + " must be of type Bool");
        if (!exp2Type.equals(variableType) || !exp3Type.equals(variableType))
            throw new RuntimeException("Expressions from conditional assignment must match");

        return typeEnv;
    }

    @Override
    public String toString() {
        return variableId + "=" + exp1 + "?" + exp2 + ":" + exp3;
    }
}
