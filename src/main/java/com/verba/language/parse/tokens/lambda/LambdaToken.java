package com.verba.language.parse.tokens.lambda;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class LambdaToken extends OperatorToken {
  public LambdaToken() {
    super(' ');
  }

  public static boolean isLambdaToken(Character firstToken, CodeStream stream) {
    return (firstToken == '-' && stream.peek() == '>');
  }

  public static LambdaToken read(Character firstToken, CodeStream stream) {
    if (firstToken == '-' && stream.peek() == '>') {
      stream.read();
      return new LambdaToken();
    } else throw new NotImplementedException();
  }

  @Override
  public String toString() {
    return "->";
  }
}
