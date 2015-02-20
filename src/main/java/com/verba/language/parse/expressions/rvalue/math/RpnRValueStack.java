package com.verba.language.parse.expressions.rvalue.math;

import com.verba.language.parse.expressions.rvalue.simple.MathExpression;

import java.util.Stack;

/**
 * Created by sircodesalot on 14-2-27.
 */

public class RpnRValueStack {
  private Stack<MathExpression> stack = new Stack<MathExpression>();

  public void push(MathExpression item) {
    this.stack.push(item);
  }

  public MathExpression pop() {
    return this.stack.pop();
  }

  public MathExpression peek() {
    return this.stack.peek();
  }

  public boolean hasItems() {
    return !stack.empty();
  }
}
