package com.verba.language.parse.tokens.operators.comparison;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-27.
 */
public abstract class CompositeComparisonToken extends OperatorToken {
  private String represenation;

  protected CompositeComparisonToken(String representation) {
    super(' ');

    this.represenation = representation;
  }

  @Override
  public String toString() {
    return this.represenation;
  }

  public static boolean isCompositeComparisonToken(Character firstToken, CodeStream stream) {
    if (stream.hasNext() == false) return false;
    else return (firstToken == '=' && stream.peek() == '=')
      || (firstToken == '!' && stream.peek() == '=')
      || (firstToken == '<' && stream.peek() == '=')
      || (firstToken == '>' && stream.peek() == '=');
  }

  public static CompositeComparisonToken read(Character firstToken, CodeStream stream) {
    if (firstToken == '=' && stream.peek() == '=') {
      stream.read();
      return new CompareEqualsToken();
    } else if (firstToken == '!' && stream.peek() == '>') {
      stream.read();
      return new NotGreaterThanToken();
    } else if (firstToken == '!' && stream.peek() == '<') {
      stream.read();

      return new NotLessThanToken();
    } else if (firstToken == '!' && stream.peek() == '=') {
      stream.read();
      return new NotEqualsToken();
    } else if (firstToken == '<' && stream.peek() == '=') {
      stream.read();
      return new LessThanEqualsToken();
    } else if (firstToken == '>' && stream.peek() == '=') {
      stream.read();
      return new GreaterThanEqualsToken();
    } else throw new NotImplementedException();
  }
}
