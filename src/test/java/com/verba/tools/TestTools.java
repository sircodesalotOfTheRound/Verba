package com.verba.tools;

import com.verba.language.parse.codestream.StringBasedCodeStream;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;

/**
 * Created by sircodesalot on 15/2/20.
 */
public class TestTools {
  public static VerbaMemoizingLexer generateLexerFromString(String expression) {
    StringBasedCodeStream codeStream = new StringBasedCodeStream(expression);
    return new VerbaMemoizingLexer("testfile.v", codeStream);
  }
}
