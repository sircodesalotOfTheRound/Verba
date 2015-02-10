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
public class SourceUnit {
  private final String path;
  private final String content;
  private final String hash;

  private SourceUnit(String path, String content) {
    this.path = path;
    this.content = content;
    this.hash = DigestUtils.sha1Hex(content);
  }

  public String path() { return this.path; }
  public String content() { return this.content; }
  public String hash() { return this.hash; }
  public Lexer lexer() { return generateCodeStream(content); }

  public static SourceUnit fromFile(String path) {
    String content = FileTools.readAllText(path);
    return new SourceUnit(path, content);
  }

  private Lexer generateCodeStream(String source) {
    CodeStream stream = new StringBasedCodeStream(source);
    return new VerbaMemoizingLexer(source, stream);
  }
}
