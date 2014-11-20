package com.verba.language.expressions.rvalue.math;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.MathOperandExpression;
import com.verba.language.expressions.rvalue.simple.MathOpExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */

// Deprecated. No longer neccesary since I'm using a register based machine.
public class RpnMap {
  private final RpnRValueStack stack = new RpnRValueStack();
  private QList<VerbaExpression> polishNotation = new QList<>();
  private final VerbaExpression parent;
  private final Lexer lexer;

  public RpnMap(VerbaExpression parent, Lexer lexer) {
    this.parent = parent;
    this.lexer = lexer;

    project();
  }

  private void project() {
    int startLine = lexer.getCurrentLine();

    while (lexer.notEOF() && lexer.getCurrentLine() == startLine) {
      // If the next item is not a math op, then try to
      // resolve it as an RValue, else break.
      if (!lexer.currentIs(MathOpToken.class)) {
        VerbaExpression expression = (VerbaExpression) MathOperandExpression.read(parent, lexer);
        if (expression == null) break;
        this.polishNotation.add(expression);

      } else if (lexer.currentIs(MathOpToken.class)) {
        this.handleMathOpToken(lexer);
      }
    }

    this.unravelStack();
  }

  private void handleMathOpToken(Lexer lexer) {
    MathOpExpression mathop = MathOpExpression.read(parent, lexer);
    int currentOpPriorityLevel = getPriorityLevel(mathop);

    if (!stack.hasItems()) stack.push(mathop);
    else {
      int topOfStackOpPriorityLevel = this.getPriorityLevel(stack.peek());
      while (topOfStackOpPriorityLevel >= currentOpPriorityLevel) {
        this.popOperationToOutput();

        if (!stack.hasItems()) break;
        else topOfStackOpPriorityLevel = this.getPriorityLevel(stack.peek());
      }

      stack.push(mathop);
    }
  }

  private void popOperationToOutput() {
    VerbaExpression operation = this.stack.pop();
    this.polishNotation.add(operation);
  }

  private int getPriorityLevel(MathOpExpression mathop) {
    LexInfo lexInfo = mathop.operator();
    MathOpToken mathOpToken = (MathOpToken) lexInfo.getToken();
    return mathOpToken.getPriorityLevel();
  }

  private void unravelStack() {
    while (stack.hasItems()) {
      polishNotation.add(stack.pop());
    }
  }

  public QIterable<VerbaExpression> getPolishNotation() {
    return this.polishNotation;
  }
}
