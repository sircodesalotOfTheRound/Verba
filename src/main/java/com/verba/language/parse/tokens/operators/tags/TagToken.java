package com.verba.language.parse.tokens.operators.tags;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-28.
 */
public abstract class TagToken extends OperatorToken {
  private String representation;

  protected TagToken(String representation) {
    super('t');
    this.representation = representation;
  }

  public String toString() {
    return this.representation;
  }

  public static boolean isTagToken(Character firstToken, CodeStream stream) {
    if (firstToken == '#' && stream.peek() == '[') return true;
    else if (firstToken == '@' && stream.peek() == '[') return true;

    else return false;
  }

  public static OperatorToken read(Character firstToken, CodeStream stream) {
    if (firstToken == '#' && stream.peek() == '[') {
      stream.read();
      return new MetaTagToken();
    } else if (firstToken == '@' && stream.peek() == '[') {
      stream.read();
      return new AspectTagToken();
    } else throw new NotImplementedException();
  }
}
