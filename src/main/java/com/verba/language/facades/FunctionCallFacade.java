package com.verba.language.facades;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.NamedValueExpression;

/**
 * Created by sircodesalot on 14/9/21.
 */
public class FunctionCallFacade {
  private final NamedValueExpression expression;

  public FunctionCallFacade(NamedValueExpression expression) {
    this.expression = expression;
  }

  // Continuation means that the return value of the function call
  // is also called. For example if a function returns another function,
  // and that function is called, like:
  //
  //  fn outer(first, second) { return fn inner(lhs, rhs) }
  //
  // is called using:
  //
  // outer(1, 2)(3, 4) # This calls outer, which returns inner which is called.
  public boolean hasCallContinuation() {
    return expression.parameters().count() > 1;
  }

  public String functionName() { return expression.name(); }

  public QIterable<VerbaExpression> primaryParameters() {
    return expression.identifier().members().first().parameterLists().first().items();
  }

  public static boolean isFunctionCall(NamedValueExpression expression) {
    return expression.hasParameters();
  }

}
