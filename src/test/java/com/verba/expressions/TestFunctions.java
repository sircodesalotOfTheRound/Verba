package com.verba.expressions;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.testtools.TestTools;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/26.
 */
public class TestFunctions {
  @Test
  public void testParameterlessAnonymousFunction() {
    VerbaMemoizingLexer lexer = TestTools.generateLexerFromString("fn { }");
    FunctionDeclarationExpression expression = VerbaExpression.read(null, lexer).as(FunctionDeclarationExpression.class);

    assert (expression.isAnonymous());
  }

  @Test
  public void testParameterizedAnonymousFunction() {
    VerbaMemoizingLexer lexer = TestTools.generateLexerFromString("fn (first, second, third) { }");
    FunctionDeclarationExpression expression = VerbaExpression.read(null, lexer).as(FunctionDeclarationExpression.class);

    assert (expression.isAnonymous());
    assert (expression.parameterSets().single().items().count() == 3);
  }

  @Test
  public void testAsyncFunction() {
    VerbaMemoizingLexer lexer = TestTools.generateLexerFromString("async function()");
    DeclarationModifierExrpression modifierExpression = VerbaExpression.read(null, lexer).as(DeclarationModifierExrpression.class);
    VerbaExpression expression = modifierExpression.modifiedExpression();

    assert (modifierExpression.modifier().is(KeywordToken.class, KeywordToken.ASYNC));
    assert (expression instanceof NamedValueExpression);
    assert (expression.as(NamedValueExpression.class)).name().equals("function");
  }
}
