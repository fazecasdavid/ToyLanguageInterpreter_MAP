package com.djf.model.statement.posibile;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyDictionary;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.expression.LogicExpression;
import com.djf.model.expression.NegationExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.statement.WhileStatement;
import com.djf.model.type.BoolType;
import com.djf.model.type.Type;

public class RepeatUntilStatement implements IStatement {

    private final IStatement stmt;
    private final IExpression exp;

    public RepeatUntilStatement(IStatement stmt, IExpression exp) {
        this.stmt = stmt;
        this.exp = exp;
    }


    @Override
    public ProgramState execute(ProgramState state) {

        var newWhileStmt = new WhileStatement(new NegationExpression(exp), stmt);
        state.getExecutionStack().push(newWhileStmt);
        state.getExecutionStack().push(stmt);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        stmt.typeCheck(new MyDictionary<>(typeEnv));
        if (!exp.typeCheck(typeEnv).equals(new BoolType()))
            throw new RuntimeException("Expression in Repeat until is not of bool type: " + exp);

        return typeEnv;
    }

    @Override
    public String toString() {
        return "Repeat(" + stmt + ")Until(" + exp + ")";
    }
}
