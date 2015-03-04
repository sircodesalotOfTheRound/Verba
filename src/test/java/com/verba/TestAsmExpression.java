package com.verba;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.interop.AsmBlockExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.tools.TestTools;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/10.
 */
public class TestAsmExpression {
  @Test
  public void testAsmExpression() {
    Lexer lexer = TestTools.generateLexerFromString("asm:x64 {  }");
    AsmBlockExpression asm = VerbaExpression.read(null, lexer).as(AsmBlockExpression.class);

    assert(asm.architecture().representation().equals("x64"));
    assert(!asm.block().hasItems());
  }
}
