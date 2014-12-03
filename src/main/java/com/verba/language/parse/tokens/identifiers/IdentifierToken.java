package com.verba.language.parse.tokens.identifiers;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.tokenization.Token;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class IdentifierToken implements Token {
  protected final String representation;

  public IdentifierToken(String representation) {
    this.representation = representation;
  }

  public static Token read(CodeStream stream) {
    StringBuilder builder = new StringBuilder();

    while (stream.hasNext()) {
      if (Character.isLetter(stream.peek())) {
        builder.append(stream.read());
        continue;
      }
      if (builder.length() > 0 && Character.isDigit(stream.peek())) {
        builder.append(stream.read());
        continue;
      }
      if (stream.peek() == '_') {
        builder.append(stream.read());
        continue;
      }

      break;
    }

    String token = builder.toString();

    if (KeywordToken.isKeyword(token)) return new KeywordToken(token);
    else if (SqlKeywordToken.isKeyword(token)) return new SqlKeywordToken(token);
    else return new IdentifierToken(builder.toString());
  }

  public String representation() {
    return this.representation;
  }

  @Override
  public String toString() {
    return this.representation;
  }
}
