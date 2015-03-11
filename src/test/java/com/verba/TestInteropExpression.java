package com.verba;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.interop.AsmBlockExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.tools.TestTools;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/10.
 */
public class TestInteropExpression {
  @Test
  public void testAsmExpression() {
    Lexer lexer = TestTools.generateLexerFromString("asm:x64 {  }");
    AsmBlockExpression asm = VerbaExpression.read(null, lexer).as(AsmBlockExpression.class);

    assert(asm.architecture().representation().equals("x64"));
    assert(!asm.block().hasItems());
  }

  @Test
  public void testInteropExpressions() {
    Lexer lexer = TestTools.generateLexerFromString("interop fn interop_function() { }");
    DeclarationModifierExrpression modifier = VerbaExpression.read(null, lexer).as(DeclarationModifierExrpression.class);

    assert(modifier.modifier().is(KeywordToken.class));
    assert(modifier.modifier().representation().equals(KeywordToken.INTEROP));

    assert(modifier.modifiedExpression().is(FunctionDeclarationExpression.class));
    assert(modifier.modifiedExpression().as(FunctionDeclarationExpression.class)
      .name().equals("interop_function"));
  }
}
