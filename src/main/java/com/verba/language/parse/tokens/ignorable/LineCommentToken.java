package com.verba.language.parse.tokens.ignorable;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.tokenization.Token;

/**
 * Created by sircodesalot on 14-5-20.
 */
public class LineCommentToken implements Token {
  private final String representation;

  public LineCommentToken(String representation) {
    this.representation = representation;
  }

  @Override
  public String toString() {
    return this.representation;
  }

  public static boolean isLineCommentToken(CodeStream stream) {
    return stream.peek().equals('#');
  }

  public static LineCommentToken read(CodeStream codeStream) {
    StringBuilder representation = new StringBuilder();
    int line = codeStream.line();

    do {
      representation.append(codeStream.read());
    } while (codeStream.hasNext() && codeStream.line() == line);

    return new LineCommentToken(representation.toString());
  }
}
