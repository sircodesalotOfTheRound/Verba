package com.verba.language.build.source;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.codestream.StringBasedCodeStream;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.tools.files.FileTools;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by sircodesalot on 15/2/10.
 */
public interface CodeUnit {
  public abstract String path();
  public abstract String content();
  public abstract String hash();

  public static CodeUnit fromFile(String path) {
    String content = FileTools.readAllText(path);
    return new SourceCodeUnit(path, content);
  }
}
