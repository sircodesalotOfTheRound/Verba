package com.verba.language.parse.expressions.rvalue.simple.tools;

import com.javalinq.implementations.QList;
import com.javalinq.tools.Partition;
import com.verba.language.parse.expressions.rvalue.simple.InfixExpression;
import com.verba.language.parse.tokens.operators.mathop.AddOpToken;
import com.verba.language.parse.tokens.operators.mathop.DivideOpToken;
import com.verba.language.parse.tokens.operators.mathop.MultiplyOpToken;
import com.verba.language.parse.tokens.operators.mathop.SubtractOpToken;
import com.verba.testtools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 15/2/24.
 */
public class InfixOperatorPrecedenceComparator {
  private static Partition<Class, InfixOperatorPrecedenceLevel> precedenceTable = new QList<>(
    new InfixOperatorPrecedenceLevel(AddOpToken.class, 1),
    new InfixOperatorPrecedenceLevel(SubtractOpToken.class, 1),
    new InfixOperatorPrecedenceLevel(MultiplyOpToken.class, 2),
    new InfixOperatorPrecedenceLevel(DivideOpToken.class, 2))
      .parition(InfixOperatorPrecedenceLevel::token);

  private static class InfixOperatorPrecedenceLevel implements Comparable<InfixOperatorPrecedenceLevel> {
    private final Class token;
    private final int level;

    public InfixOperatorPrecedenceLevel(Class token, int level) {
      this.token = token;
      this.level = level;
    }

    public Class token() { return this.token; }
    public int level() { return this.level; }

    @Override
    public int compareTo(InfixOperatorPrecedenceLevel rhs) {
      if (rhs == null) {
        return -1;
      } else {
        return rhs.level - this.level;
      }
    }
  }

  private static Class determineInfixExpressionOperator(InfixExpression expression) {
    if (expression == null) {
      throw new CompilerException("comparePrecedence arguments must not be null");
    } else {
      return expression.operator().getToken().getClass();
    }
  }

  private static boolean expressionHasValidOperator(InfixExpression expression) {
    Class resolvedType = determineInfixExpressionOperator(expression);
    return precedenceTable.containsKey(resolvedType);
  }

  private static InfixOperatorPrecedenceLevel determinePrecedenceLevel(InfixExpression expression) {
    Class operatorTokenType = determineInfixExpressionOperator(expression);
    return precedenceTable.get(operatorTokenType).single();
  }

  public static int comparePrecedence(InfixExpression lhs, InfixExpression rhs) {
    if (!expressionHasValidOperator(lhs) || !expressionHasValidOperator(rhs)) {
      throw new CompilerException("Invalid Operator Type passed");
    }

    InfixOperatorPrecedenceLevel lhsLevel = determinePrecedenceLevel(lhs);
    InfixOperatorPrecedenceLevel rhsLevel = determinePrecedenceLevel(rhs);
    return lhsLevel.compareTo(rhsLevel);
  }
}
