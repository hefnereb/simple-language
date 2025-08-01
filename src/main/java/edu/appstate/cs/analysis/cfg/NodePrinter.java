package edu.appstate.cs.analysis.cfg;

import edu.appstate.cs.analysis.ast.*;
import edu.appstate.cs.analysis.visitor.AnalysisVisitor;

import java.util.ArrayList;
import java.util.List;

public class NodePrinter implements AnalysisVisitor<String> {
    @Override
    public String visitStmtList(StmtList stmtList) {
        StringBuffer buf = new StringBuffer();
        return buf.toString();
    }

    @Override
    public String visitElseIfList(ElseIfList elseIfList) {
        StringBuffer buf = new StringBuffer();
        return buf.toString();
    }

    @Override
    public String visitExprList(ExprList exprList) {
        StringBuffer buf = new StringBuffer();
        List<String> results  = new ArrayList<>();
        for (Expr e : exprList) {
            results.add(e.accept(this));
        }
        return String.join(", ",  results);
    }

    @Override
    public String visitAssignStmt(AssignStmt assignStmt) {
        return String.format("%s = %s;", assignStmt.getIdent(), assignStmt.getExpr().accept(this));
    }

    @Override
    public String visitIntLiteral(IntLiteral intLiteral) {
        return intLiteral.getNum().toString();
    }

    @Override
    public String visitBooleanLiteral(BooleanLiteral booleanLiteral) {
        return booleanLiteral.getValue() ? "True" : "False";
    }

    @Override
    public String visitPlusExpr(PlusExpr plusExpr) {
        return String.format("(%s + %s)", plusExpr.getLeft().accept(this), plusExpr.getRight().accept(this));
    }

    @Override
    public String visitSubExpr(SubExpr subExpr) {
        return String.format("(%s - %s)", subExpr.getLeft().accept(this), subExpr.getRight().accept(this));
    }

    @Override
    public String visitMultExpr(MultExpr multExpr) {
        return String.format("(%s * %s)", multExpr.getLeft().accept(this), multExpr.getRight().accept(this));
    }

    @Override
    public String visitDivExpr(DivExpr divExpr) {
        return String.format("(%s / %s)", divExpr.getLeft().accept(this), divExpr.getRight().accept(this));
    }

    public String visitEqualExpr(EqualExpr equalExpr) {
        return String.format("%s == %s", equalExpr.getLeft().accept(this), equalExpr.getRight().accept(this));
    }

    @Override
    public String visitIdentExpr(IdentExpr identExpr) {
        return identExpr.getIdent();
    }

    @Override
    public String visitForStmt(ForStmt forStmt) {
        return String.format("for %s in %s {\n %s\n}",
                forStmt.getIdent(),
                forStmt.getExpr().accept(this),
                forStmt.getStmts().accept(this));
    }

    @Override
    public String visitWhileStmt(WhileStmt whileStmt) {
        return String.format("while %s {\n %s\n}",
                whileStmt.getExpr().accept(this),
                whileStmt.getStmts().accept(this)
        );
    }

    @Override
    public String visitExprStmt(ExprStmt exprStmt) {
        return String.format("%s;", exprStmt.getExpr().accept(this));
    }

    @Override
    public String visitNotEqlExpr(NotEqlExpr notEqlExpr) {
        return String.format("(%s != %s)", notEqlExpr.getLeft().accept(this), notEqlExpr.getRight().accept(this));
    }

    @Override
    public String visitDeclStmt(DeclStmt declStmt) {
        if (declStmt.getExpr() != null) {
            return String.format("var %s = %s;", declStmt.getIdent(), declStmt.getExpr().accept(this));
        } else {
            return String.format("var %s;", declStmt.getIdent());
        }
    }

    public String visitAndExpr(AndExpr andExpr) {
        return String.format("(%s and %s)",
                andExpr.getLeft().accept(this), andExpr.getRight().accept(this));
    }

    @Override
    public String visitLtExpr(LtExpr ltExpr) {
        return String.format("(%s < %s)", ltExpr.getLeft().accept(this), ltExpr.getRight().accept(this));
    }

    @Override
    public String visitLtEqExpr(LtEqExpr ltEqExpr) {
        return String.format("(%s <= %s)", ltEqExpr.getLeft().accept(this), ltEqExpr.getRight().accept(this));
    }

    @Override
    public String visitGtExpr(GtExpr gtExpr) {
        return String.format("(%s > %s)", gtExpr.getLeft().accept(this), gtExpr.getRight().accept(this));
    }

    @Override
    public String visitGtEqExpr(GtEqExpr gtEqExpr) {
        return String.format("(%s >= %s)", gtEqExpr.getLeft().accept(this), gtEqExpr.getRight().accept(this));
    }

    @Override
    public String visitListExpr(ListExpr listExpr) {
        return String.format("[%s]", listExpr.getList().accept(this));
    }

    @Override
    public String visitNegExpr(NegExpr negExpr) {
        return String.format("(~%s)", negExpr.getExpr().accept(this));
    }

    @Override
    public String visitElseIf(ElseIf elseIf) {
        return String.format("else-if %s then {\n%s}\n",
                elseIf.getCondition().accept(this),
                elseIf.getBody().accept(this));
    }

    @Override
    public String visitIfStmt(IfStmt ifStmt) {
        StringBuffer buf = new StringBuffer();

        String result = String.format("if %s then {\n%s}",
                ifStmt.getCondition().accept(this),
                ifStmt.getThenBody().accept(this));
        buf.append(result);

        if (ifStmt.getElseIfs() != null) {
            buf.append(ifStmt.getElseIfs().accept(this));
        }

        if (ifStmt.getElseBody() != null) {
            buf.append(String.format("else {\n%s}", ifStmt.getElseBody().accept(this)));
        }

        buf.append("\n");
        return buf.toString();
    }

    @Override
    public String visitNotExpr(NotExpr notExpr) {
        return String.format("not %s", notExpr.getExpr().accept(this));
    }

    @Override
    public String visitOrExpr(OrExpr orExpr) {
        return String.format("%s or %s",
                orExpr.getLeftExpr(), orExpr.getRightExpr());
    }

    @Override
    public String visitReturnStmt(ReturnStmt returnStmt) {
        return String.format("return %s;", returnStmt.getRetExpr());
    }
}
