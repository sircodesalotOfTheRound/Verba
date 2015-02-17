package com.verba;

import com.verba.language.parse.codestream.StringBasedCodeStream;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.rvalue.simple.MathOpExpression;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.language.parse.tokens.operators.mathop.AddOpToken;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/17.
 */
public class TestMathExpressions {
  @Test
  public void testMathExpressions() {
    StringBasedCodeStream codeStream = new StringBasedCodeStream("x + y");
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("testfile.v", codeStream);
    NamedValueExpression x = (NamedValueExpression)VerbaExpression.read(null, lexer);
    MathOpExpression plus = (MathOpExpression)VerbaExpression.read(null, lexer);
    NamedValueExpression y = (NamedValueExpression)VerbaExpression.read(null, lexer);

    assert(plus.operator().is(AddOpToken.class));
  }
}
