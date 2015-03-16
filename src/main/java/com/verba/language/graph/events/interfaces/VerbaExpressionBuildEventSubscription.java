package com.verba.language.graph.events.interfaces;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.violations.ValidationError;
import com.verba.language.parse.violations.ValidationViolation;
import com.verba.language.parse.violations.ValidationViolationList;
import com.verba.language.parse.violations.ValidationWarning;
import com.verba.language.parse.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14/11/22.
 */
public abstract class VerbaExpressionBuildEventSubscription<T> implements VerbaExpressionBuildEventSubscriptionBase {
  private final T expression;

  public VerbaExpressionBuildEventSubscription(T expression) {
    this.expression = expression;
  }

  protected T expression() { return this.expression; }

}
