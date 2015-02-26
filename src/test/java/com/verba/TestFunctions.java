package com.verba;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.tools.TestTools;
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
}
