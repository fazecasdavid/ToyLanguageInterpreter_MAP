package com.djf.model.statement.posibile;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyDictionary;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.statement.WhileStatement;
import com.djf.model.type.BoolType;
import com.djf.model.type.Type;

public class DoWhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement statement;

    public DoWhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {

        var newWhileStatement = new WhileStatement(expression, statement);
        state.getExecutionStack().push(newWhileStatement);
        state.getExecutionStack().push(statement);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        if(!expression.typeCheck(typeEnv).equals(new BoolType()))
            throw new RuntimeException("The condition of DO-WHILE has not the type bool");

        statement.typeCheck(new MyDictionary<>(typeEnv));
        return typeEnv;
    }
    @Override
    public String toString() {
        return "DO{" + statement + "} WHILE(" + expression + ")";
    }
}
