package com.verba.test.lexing;

import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.LambdaToken;
import com.verba.language.lexing.tokens.operators.OperatorToken;
import com.verba.language.lexing.tokens.operators.assignment.*;
import com.verba.language.lexing.tokens.operators.comparison.*;
import com.verba.language.lexing.tokens.operators.mathop.*;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-3-21.
 */
public class Operators {
    @Test
    public void basicperatorTest() {
        Lexer lexer = GeneralLexing.generateLexerFromString("+ - * / % ->");

        assert (lexer.currentIs(OperatorToken.class, "+"));
        assert (lexer.currentIs(MathOpToken.class, "+"));
        assert (lexer.currentIs(AddOpToken.class, "+"));

        lexer.advance();

        assert (lexer.currentIs(MathOpToken.class, "-"));
        assert (lexer.currentIs(SubtractOpToken.class, "-"));
        lexer.advance();

        assert (lexer.currentIs(MathOpToken.class, "*"));
        assert (lexer.currentIs(MultiplyOpToken.class, "*"));
        lexer.advance();

        assert (lexer.currentIs(MathOpToken.class, "/"));
        assert (lexer.currentIs(DivideOpToken.class, "/"));

        lexer.advance();

        assert (lexer.currentIs(MathOpToken.class, "%"));
        assert (lexer.currentIs(ModuloOpToken.class, "%"));
        lexer.advance();

        assert (lexer.currentIs(OperatorToken.class, "->"));
        assert (lexer.currentIs(LambdaToken.class, "->"));
        lexer.advance();

        assert (lexer.isEOF());
    }


    @Test
    public void assignmentOperatorTest() {
        Lexer lexer = GeneralLexing.generateLexerFromString("= += -= *= /= %=");

        assert (lexer.currentIs(OperatorToken.class, "="));
        assert (lexer.currentIs(AssignmentToken.class, "="));
        assert (lexer.currentIs(AssignmentOperatorToken.class, "="));

        lexer.advance();

        assert (lexer.currentIs(OperatorToken.class, "+="));
        assert (lexer.currentIs(AssignmentToken.class, "+="));
        assert (lexer.currentIs(CompositeAssignmentToken.class, "+="));
        assert (lexer.currentIs(PlusEqualsToken.class, "+="));

        lexer.advance();

        assert (lexer.currentIs(OperatorToken.class, "-="));
        assert (lexer.currentIs(AssignmentToken.class, "-="));
        assert (lexer.currentIs(CompositeAssignmentToken.class, "-="));
        assert (lexer.currentIs(MinusEqualsToken.class, "-="));

        lexer.advance();

        assert (lexer.currentIs(OperatorToken.class, "*="));
        assert (lexer.currentIs(AssignmentToken.class, "*="));
        assert (lexer.currentIs(CompositeAssignmentToken.class, "*="));
        assert (lexer.currentIs(TimesEqualsToken.class, "*="));

        lexer.advance();

        assert (lexer.currentIs(OperatorToken.class, "/="));
        assert (lexer.currentIs(AssignmentToken.class, "/="));
        assert (lexer.currentIs(CompositeAssignmentToken.class, "/="));
        assert (lexer.currentIs(DivideEqualsToken.class, "/="));

        lexer.advance();

        assert (lexer.currentIs(OperatorToken.class, "%="));
        assert (lexer.currentIs(AssignmentToken.class, "%="));
        assert (lexer.currentIs(CompositeAssignmentToken.class, "%="));
        assert (lexer.currentIs(ModuloEqualsToken.class, "%="));

        lexer.advance();

        assert (lexer.isEOF());
    }

    @Test
    public void comparisonOperatorTest() {
        Lexer lexer = GeneralLexing.generateLexerFromString("== != <= >=");

        assert (lexer.currentIs(OperatorToken.class, "=="));
        assert (lexer.currentIs(CompositeComparisonToken.class, "=="));
        assert (lexer.currentIs(CompareEqualsToken.class, "=="));
        lexer.advance();

        assert (lexer.currentIs(CompositeComparisonToken.class, "!="));
        assert (lexer.currentIs(NotEqualsToken.class, "!="));
        lexer.advance();

        assert (lexer.currentIs(CompositeComparisonToken.class, "<="));
        assert (lexer.currentIs(LessThanEqualsToken.class, "<="));
        lexer.advance();

        assert (lexer.currentIs(CompositeComparisonToken.class, ">="));
        assert (lexer.currentIs(GreaterThanEqualsToken.class, ">="));
        lexer.advance();

        assert (lexer.isEOF());
    }
}
