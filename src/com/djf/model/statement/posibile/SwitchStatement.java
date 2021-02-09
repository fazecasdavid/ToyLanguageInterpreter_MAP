package com.djf.model.statement.posibile;

import com.djf.model.ProgramState;
import com.djf.model.adt.MyDictionary;
import com.djf.model.adt.MyIDictionary;
import com.djf.model.expression.IExpression;
import com.djf.model.expression.RelationalExpression;
import com.djf.model.statement.IStatement;
import com.djf.model.statement.IfStatement;
import com.djf.model.type.Type;


public class SwitchStatement implements IStatement {

    private final IExpression exp;
    private final IExpression exp1;
    private final IStatement stmt1;
    private final IExpression exp2;
    private final IStatement stmt2;
    private final IStatement stmt3;

    public SwitchStatement(IExpression exp, IExpression exp1, IStatement stmt1, IExpression exp2, IStatement stmt2, IStatement stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }


    @Override
    public ProgramState execute(ProgramState state) {

        var newIfStatement = new IfStatement(new RelationalExpression(exp, exp1, "=="), stmt1,
                new IfStatement(new RelationalExpression(exp, exp2, "=="), stmt2, stmt3));

        state.getExecutionStack().push(newIfStatement);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {

        var type0 = exp.typeCheck(typeEnv);
        var type1 = exp1.typeCheck(typeEnv);
        var type2 = exp2.typeCheck(typeEnv);

        if(!(type0.equals(type1) && type1.equals(type2)))
            throw new RuntimeException("Type of expressions do not match in Switch Statement");


        stmt1.typeCheck(new MyDictionary<>(typeEnv));
        stmt2.typeCheck(new MyDictionary<>(typeEnv));
        stmt3.typeCheck(new MyDictionary<>(typeEnv));

        return typeEnv;
    }

    @Override
    public String toString() {
        return "Switch(" + exp + ") (case " + exp1 + ": " + stmt1 + ") (case" + exp2 + ": "
                + stmt2 + ") (default:" + stmt3 + ")";
    }
}
