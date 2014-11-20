package com.verba.language.graph.imagegen.function.variables;

import com.verba.language.expressions.VerbaExpression;

import java.util.HashMap;

/**
 * Created by sircodesalot on 14/9/21.
 */
public class VariableLifetimeMap {
  private final HashMap<String, VariableLifetime> lifetimes = new HashMap<>();


  public void updateLifetime(VerbaExpression expression) {
    if (!lifetimes.containsKey(expression.text())) {
      createLifetime(expression);
    } else {
      VariableLifetime variableLifetime = lifetimes.get(expression.text());
      variableLifetime.updateEndingLineNumber(expression);
    }
  }

  private void createLifetime(VerbaExpression expression) {
    lifetimes.put(expression.text(), new VariableLifetime(expression));
  }

  public boolean containsLifetime(VerbaExpression expression) {
    return this.lifetimes.containsKey(expression.text());
  }

  public VariableLifetime getLifetime(VerbaExpression expression) {
    return lifetimes.get(expression.text());
  }
}
