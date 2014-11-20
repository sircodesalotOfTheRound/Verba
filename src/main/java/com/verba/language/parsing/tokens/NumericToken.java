package com.verba.language.parsing.tokens;

import com.verba.language.parsing.codestream.CodeStream;
import com.verba.language.parsing.tokenization.Token;

/**
 * Created by sircodesalot on 14-2-16.
 */
public class NumericToken implements Token {

  private String representation;

  private NumericToken(String representation) {
    this.representation = representation;
  }

  @Override
  public String toString() {
    return this.representation;
  }

  public static Token read(CodeStream stream) {
    StringBuilder builder = new StringBuilder();
    boolean periodSpotted = false;
    boolean altBaseSpotted = false;

    while (stream.hasNext()) {
      if (Character.isDigit(stream.peek())) {
        builder.append(stream.read());
        continue;
      }
      if ((stream.peek() == 'x' || stream.peek() == 'b')
        && ((altBaseSpotted == false) && (periodSpotted == false))) {

        builder.append(stream.read());
        altBaseSpotted = true;
        continue;
      }

      if ((stream.peek() == '.') && (altBaseSpotted == false) && (periodSpotted == false)) {
        builder.append(stream.read());
        periodSpotted = true;
        continue;
      }

      break;
    }

    return new NumericToken(builder.toString());
  }

}
