package com.verba.language.graph.expressions.events.interfaces;

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
