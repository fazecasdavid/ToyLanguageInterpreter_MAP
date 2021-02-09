package com.djf.model.statement.posibile;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyDictionary;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.statement.CompoundStatement;
import com.djf.model.statement.IStatement;
import com.djf.model.statement.WhileStatement;
import com.djf.model.type.BoolType;
import com.djf.model.type.Type;
import com.djf.model.value.Value;

public class ForStatement implements IStatement {
    private final IStatement initializeStatement;
    private final IExpression conditionExpression;
    private final IStatement continueStatement;

    private final IStatement statement;

    public ForStatement(IStatement initializeStatement, IExpression conditionExpression, IStatement continueStatement, IStatement statement) {
        this.initializeStatement = initializeStatement;
        this.conditionExpression = conditionExpression;
        this.continueStatement = continueStatement;

        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {


        var newWhileStatement = new WhileStatement(conditionExpression, new CompoundStatement(statement, continueStatement));
        state.getExecutionStack().push(newWhileStatement);
        state.getExecutionStack().push(initializeStatement);


        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        var typeEnvInner = initializeStatement.typeCheck(new MyDictionary<>(typeEnv));
        if (!conditionExpression.typeCheck(typeEnvInner).equals(new BoolType()))
            throw new RuntimeException("The condition of FOR has not the type bool");
        continueStatement.typeCheck(typeEnvInner);
        statement.typeCheck(typeEnvInner);

        return typeEnv;
    }

    @Override
    public String toString() {
        return "For(" + initializeStatement + "; " + conditionExpression + "; " + continueStatement + ") { " +statement + " }";
    }
}
