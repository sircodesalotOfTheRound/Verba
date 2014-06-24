package com.verba.test.parsing.containers;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.containers.set.SetDeclarationExpression;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.lexing.Lexer;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-21.
 */
public class SetContainerParsing {
    @Test
    public void testSetContainer() {
        Lexer lexer = GeneralLexing.generateLexerFromString("val item : Set<Integer> = { 1, 2, 3, 4, 5 }");
        ValDeclarationStatement valStatement = (ValDeclarationStatement) VerbaExpression.read(null, lexer);

        assert (valStatement.name().equals("item"));
        assert (valStatement.hasTypeConstraint());
        assert (valStatement.typeDeclaration().representation().equals("Set<Integer>"));

        assert (valStatement.rvalue() instanceof SetDeclarationExpression);

        SetDeclarationExpression set = (SetDeclarationExpression) valStatement.rvalue();
        assert (set.items().all(item -> item instanceof NumericExpression));
        assert (set.items().cast(NumericExpression.class).all(number -> number.asInt() > 0));
        assert (set.items().cast(NumericExpression.class).all(number -> number.asInt() < 6));
    }
}
