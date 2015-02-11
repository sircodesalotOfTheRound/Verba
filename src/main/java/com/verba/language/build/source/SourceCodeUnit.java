package com.verba.language.build.source;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.codestream.StringBasedCodeStream;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by sircodesalot on 15/2/10.
 */
public class SourceCodeUnit implements CodeUnit {
  private final String path;
  private final String content;
  private final String hash;

  public SourceCodeUnit(String path, String content) {
    this.path = path;
    this.content = content;
    this.hash = DigestUtils.sha1Hex(content);
  }

  @Override
  public String path() { return this.path; }

  @Override
  public String content() { return this.content; }

  @Override
  public String hash() { return this.hash; }
  public Lexer lexer() { return generateCodeStream(content); }


  private Lexer generateCodeStream(String source) {
    CodeStream stream = new StringBasedCodeStream(source);
    return new VerbaMemoizingLexer(source, stream);
  }
}
