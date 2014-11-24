package com.verba.language.parsing.tokens.operators.assignment;

import com.verba.language.parsing.codestream.CodeStream;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;
import com.verba.language.parsing.tokens.operators.mathop.PowerEqualsToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class AssignmentToken extends OperatorToken {
  String representation;

  public AssignmentToken(String representation) {
    super(' ');

    this.representation = representation;
  }

  @Override
  public String toString() {
    return this.representation;
  }

  public static boolean isAssignmentToken(Character firstToken, CodeStream stream) {
    if (firstToken == '=' && !stream.hasNext()) return true;

    if (!stream.hasNext()) return false;

    if (firstToken == '+' && stream.peek() == '=') return true;
    else if (firstToken == '-' && stream.peek() == '=') return true;
    else if (firstToken == '*' && stream.peek() == '=') return true;
    else if (firstToken == '/' && stream.peek() == '=') return true;
    else if (firstToken == '%' && stream.peek() == '=') return true;
    else if (firstToken == '^' && stream.peek() == '=') return true;
    else if (firstToken == '=' && stream.peek() != '=') return true;

    return false;
  }

  public static AssignmentToken read(Character firstToken, CodeStream stream) {
    if (firstToken == '=' && (!stream.hasNext() || stream.peek() != '=')) return new AssignmentOperatorToken();

    if (!stream.hasNext()) throw new NotImplementedException();

    if (firstToken == '+' && stream.peek() == '=') {
      stream.read();
      return new PlusEqualsToken();
    } else if (firstToken == '-' && stream.peek() == '=') {
      stream.read();
      return new MinusEqualsToken();
    } else if (firstToken == '*' && stream.peek() == '=') {
      stream.read();
      return new TimesEqualsToken();
    } else if (firstToken == '/' && stream.peek() == '=') {
      stream.read();
      return new DivideEqualsToken();
    } else if (firstToken == '%' && stream.peek() == '=') {
      stream.read();
      return new ModuloEqualsToken();
    } else if (firstToken == '^' && stream.peek() == '=') {
      stream.read();
      return new PowerEqualsToken();
    }

    throw new NotImplementedException();
  }
}
