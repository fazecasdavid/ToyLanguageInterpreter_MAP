package com.djf.view;

import com.djf.model.expression.*;
import com.djf.model.statement.*;
import com.djf.model.statement.barrier.AwaitStatement;
import com.djf.model.statement.barrier.NewBarrierStatement;
import com.djf.model.statement.file.*;
import com.djf.model.statement.heap.*;
import com.djf.model.statement.posibile.*;
import com.djf.model.type.*;
import com.djf.model.value.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilProgramStatements {
    public static List<IStatement> getProgramStatementsList() {
        List<IStatement> programStates = new ArrayList<>();
        programStates.add(getExample1());
        programStates.add(getExample2());
        programStates.add(getExample3());
        programStates.add(getExample4());
        programStates.add(getExample5());
        programStates.add(getExample6());
        programStates.add(getExample7());
        programStates.add(getExample8());
        programStates.add(getExample9());
        programStates.add(getExample10());
        programStates.add(getExample11());
        programStates.add(getExample12());
        programStates.add(getExample13());
        programStates.add(getExample14());
//        programStates.add(getExample15());
//        programStates.add(getExample16());
        programStates.add(getExample17());
        return programStates;
    }

//    Ref  int v1; Ref  int v2; Ref  int v3; int cnt;
//    new(v1,2);new(v2,3);new(v3,4);newBarrier(cnt,rH(v2));
//
//    fork((await(cnt);wh(v1,rh(v1)*10)); print(rh(v1)));
//    fork((await(cnt);wh(v2,rh(v2)*10));  wh(v2,rh(v2)*10));print(rh(v2)));
//
//    await(cnt);
//    print(rH(v3))
    public static IStatement getExample17() {
        return assemble(
                new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new VariableDeclarationStatement("v2", new RefType(new IntType())),
                new VariableDeclarationStatement("v3", new RefType(new IntType())),
                new VariableDeclarationStatement("cnt", new IntType()),
                new NewStatement("v1", new ValueExpression(new IntValue(2))),
                new NewStatement("v2", new ValueExpression(new IntValue(3))),
                new NewStatement("v3", new ValueExpression(new IntValue(4))),
                new NewBarrierStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),

                new ForkStatement(assemble(
                        new AwaitStatement("cnt"),
                        new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v1")))
                )),
                new ForkStatement(assemble(
                        new AwaitStatement("cnt"),
                        new WriteHeapStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                        new WriteHeapStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v2")))
                        )),
                new AwaitStatement("cnt"),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v3")))
        );
    }


    public static IStatement getExample1() {
        return assemble(
                new VariableDeclarationStatement("varf", new StringType()),
//                new AssignmentStatement("varf", new ValueExpression(new IntValue(1))),
                new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                new OpenRFile(new VariableExpression("varf")),
                new VariableDeclarationStatement("varc", new IntType()),
                new ReadFile(new VariableExpression("varf"), "varc"),
                new PrintStatement(new VariableExpression("varc")),
                new ReadFile(new VariableExpression("varf"), "varc"),
                new PrintStatement(new VariableExpression("varc")),
                new CloseRFile(new VariableExpression("varf"))
        );
    }

    public static IStatement getExample2() {
        return assemble(
                new VariableDeclarationStatement("v", new IntType()),
//                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                new AssignmentStatement("v", new ValueExpression(new StringValue("Hehe"))),
                new PrintStatement(new VariableExpression("v"))
        );
    }

    public static IStatement getExample3() {
        return assemble(
                new VariableDeclarationStatement("a", new IntType()),
                new VariableDeclarationStatement("b", new IntType()),
                new AssignmentStatement("a", new ValueExpression(new IntValue(10))),
                new AssignmentStatement("b", new ValueExpression(new IntValue(12))),
                new IfStatement(new RelationalExpression(new VariableExpression("a"), new VariableExpression("b"), "<="),
                        // then
                        new PrintStatement(new VariableExpression("a")),
                        // else
                        new PrintStatement(new VariableExpression("b")))
        );
    }

    public static IStatement getExample4() {
        return assemble(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                        assemble(
                                new PrintStatement(new VariableExpression("v")),
                                new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                        )
                ),
                new PrintStatement(new VariableExpression("v"))
        );
    }

    public static IStatement getExample5() {
        return assemble(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                new NewStatement("a", new VariableExpression("v")),
                new NewStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
        );
    }

    public static IStatement getExample6() {
        return assemble(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v", new ValueExpression(new IntValue(20))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))
        );
    }

    public static IStatement getExample7() {
        return assemble(
                new VariableDeclarationStatement("a", new RefType(new IntType())),
                new VariableDeclarationStatement("b", new RefType(new IntType())),
                new VariableDeclarationStatement("c", new RefType(new IntType())),
                new VariableDeclarationStatement("d", new RefType(new IntType())),
                new NewStatement("a", new ValueExpression(new IntValue(10))),
                new NewStatement("b", new ValueExpression(new IntValue(20))),
                new NewStatement("c", new ValueExpression(new IntValue(30))),
                new NewStatement("d", new ValueExpression(new IntValue(40))),
                new AssignmentStatement("b", new VariableExpression("a")),
                new AssignmentStatement("c", new VariableExpression("a")),
                new AssignmentStatement("d", new VariableExpression("a")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
    }


    public static IStatement getExample8() {
        return assemble(
                new VariableDeclarationStatement("v", new IntType()),
                new VariableDeclarationStatement("a", new RefType(new IntType())),
//                new AssignmentStatement("v", new ValueExpression(new BoolValue(true))),
                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                new NewStatement("a", new ValueExpression(new IntValue(22))),
                new ForkStatement(assemble(
                        new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                        new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                        new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                )),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
    }

    public static IStatement getExample9() {
        return assemble(
                new VariableDeclarationStatement("a", new RefType(new IntType())),
                new NewStatement("a", new ValueExpression(new IntValue(20))),
                new ForkStatement(assemble(
                        new NewStatement("a", new ValueExpression(new IntValue(30))),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                )),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
    }

    public static IStatement getExample10() {
        return assemble(
                new ForkStatement(assemble(
                        new PrintStatement(new ValueExpression(new StringValue("Hello a1"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello a2"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello a3"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello a4")))
                )),
                new ForkStatement(assemble(
                        new PrintStatement(new ValueExpression(new StringValue("Hello b1"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello b2"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello b3"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello b4")))
                )),
                new ForkStatement(assemble(
                        new PrintStatement(new ValueExpression(new StringValue("Hello c1"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello c2"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello c3"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello c4")))
                )),
                new ForkStatement(assemble(
                        new PrintStatement(new ValueExpression(new StringValue("Hello d1"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello d2"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello d3"))),
                        new PrintStatement(new ValueExpression(new StringValue("Hello d4")))
                ))

        );

    }

    public static IStatement getExample11() {
        return assemble(
                new VariableDeclarationStatement("a", new RefType(new IntType())),
                new NewStatement("a", new ValueExpression(new IntValue(20))),
                new ForStatement(assemble(new VariableDeclarationStatement("v", new IntType()), new AssignmentStatement("v", new ValueExpression(new IntValue(0)))),
                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "<"),
                        new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))),

                        new ForkStatement(assemble(new PrintStatement(new VariableExpression("v")),
                                new AssignmentStatement("v", new ArithmeticExpression('*', new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a"))))))
                ),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
    }

    public static IStatement getExample12() {
        return assemble(
                new VariableDeclarationStatement("a", new RefType(new IntType())),
                new VariableDeclarationStatement("b", new RefType(new IntType())),
                new VariableDeclarationStatement("v", new IntType()),
                new NewStatement("a", new ValueExpression(new IntValue(0))),
                new NewStatement("b", new ValueExpression(new IntValue(0))),

                new WriteHeapStatement("a", new ValueExpression(new IntValue(1))),
                new WriteHeapStatement("b", new ValueExpression(new IntValue(2))),

                new ConditionalAssignmentStatement("v", new RelationalExpression(new ReadHeapExpression(new VariableExpression("a")), new ReadHeapExpression(new VariableExpression("b")), "<"),
                        new ValueExpression(new IntValue(100)),
                        new ValueExpression(new IntValue(200))),

                new PrintStatement(new VariableExpression("v")),

                new ConditionalAssignmentStatement("v", new RelationalExpression(new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("b")), new ValueExpression(new IntValue(2))), new ReadHeapExpression(new VariableExpression("a")), ">"),
                        new ValueExpression(new IntValue(100)),
                        new ValueExpression(new IntValue(200))),

                new PrintStatement(new VariableExpression("v"))

        );
    }

    public static IStatement getExample13() {
        return assemble(
                new VariableDeclarationStatement("a", new IntType()),
                new VariableDeclarationStatement("b", new IntType()),
                new VariableDeclarationStatement("c", new IntType()),
                new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
                new AssignmentStatement("c", new ValueExpression(new IntValue(5))),

                new SwitchStatement(new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
                        new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")),
                        assemble(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                        new ValueExpression(new IntValue(10)),
                        assemble(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
                        new PrintStatement(new ValueExpression(new IntValue(300)))
                ),

                new PrintStatement(new ValueExpression(new IntValue(300)))
        );
    }
//    int v; int x; int y; v=0;
//    (repeat (fork(print(v);v=v-1);v=v+1) until v==3);
//    x=1;nop;y=3;nop;
//    print(v*10)
    public static IStatement getExample14() {
        return assemble(
                new VariableDeclarationStatement("v", new IntType()),
                new VariableDeclarationStatement("x", new IntType()),
                new VariableDeclarationStatement("y", new IntType()),
                new AssignmentStatement("v", new ValueExpression(new IntValue(0))),

                new RepeatUntilStatement(assemble(
                        new ForkStatement(assemble(
                                new PrintStatement(new VariableExpression("v")),
                                new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                        new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))),
//                        new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(3)))),
                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "==")),

                new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
                new NopStatement(),
                new AssignmentStatement("y", new ValueExpression(new IntValue(3))),
                new NopStatement(),
                new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))

        );
    }

    public static IStatement getExample15() {
        return assemble(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                new ForkStatement(assemble(
                        new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))),
                        new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))),
                        new PrintStatement(new VariableExpression("v"))
                )),
                new SleepStatement(new ValueExpression(new IntValue(10))),
                new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))
        );
    }

    public static IStatement getExample16() {
        return assemble(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                new DoWhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                        assemble(
                                new PrintStatement(new VariableExpression("v")),
                                new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                        )
                ),
                new DoWhileStatement(new ValueExpression(new BoolValue(false)), new PrintStatement(new ValueExpression(new StringValue("Intra"))))

        );
    }


    public static IStatement assemble(IStatement... statements) {
        if (statements.length == 0)
            return new NopStatement();
        if (statements.length == 1)
            return statements[0];
        if (statements.length == 2)
            return new CompoundStatement(statements[0], statements[1]);

        return new CompoundStatement(
                statements[0],
                assemble(Arrays.copyOfRange(statements, 1, statements.length))
        );
    }

}
