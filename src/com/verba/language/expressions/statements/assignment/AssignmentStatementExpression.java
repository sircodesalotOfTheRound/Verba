package com.verba.language.expressions.statements.assignment;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.RValueExpression;
import com.verba.language.expressions.categories.StatementExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexInfo;
import com.verba.language.lexing.tokens.operators.OperatorToken;
import com.verba.language.lexing.tokens.operators.assignment.AssignmentToken;
import com.verba.language.lexing.tokens.operators.assignment.CompositeAssignmentToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class AssignmentStatementExpression extends VerbaExpression implements StatementExpression {
    TypeDeclarationExpression lvalue;
    LexInfo operation;
    RValueExpression rvalue;

    public AssignmentStatementExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        this.lvalue = TypeDeclarationExpression.read(this, lexer);

        if (lexer.currentIs(OperatorToken.class, "=")
            || lexer.currentIs(CompositeAssignmentToken.class)) {

            this.operation = lexer.readCurrentAndAdvance(AssignmentToken.class);
        }

        this.rvalue = RValueExpression.read(this, lexer);
    }

    public static AssignmentStatementExpression read(VerbaExpression parent, Lexer lexer) {
        return new AssignmentStatementExpression(parent, lexer);
    }

    public TypeDeclarationExpression lvalue() {
        return this.lvalue;
    }

    public LexInfo operation() {
        return this.operation;
    }

    public RValueExpression rvalue() {
        return this.rvalue;
    }

}
