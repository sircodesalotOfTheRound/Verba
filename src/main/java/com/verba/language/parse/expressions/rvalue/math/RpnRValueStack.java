package com.verba.language.parse.expressions.rvalue.math;

import com.verba.language.parse.expressions.rvalue.simple.InfixExpression;

import java.util.Stack;

/**
 * Created by sircodesalot on 14-2-27.
 */

public class RpnRValueStack {
  private Stack<InfixExpression> stack = new Stack<InfixExpression>();

  public void push(InfixExpression item) {
    this.stack.push(item);
  }

  public InfixExpression pop() {
    return this.stack.pop();
  }

  public InfixExpression peek() {
    return this.stack.peek();
  }

  public boolean hasItems() {
    return !stack.empty();
  }
}
