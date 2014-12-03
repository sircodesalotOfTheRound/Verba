package com.verba.language.build.event.subscriptions;

import com.verba.language.build.event.ExpressionBuildEventSubscription;
import com.verba.language.parse.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class StaticSpaceExpressionBuildEventSubscription extends ExpressionBuildEventSubscription<StaticSpaceExpression> {
  public StaticSpaceExpressionBuildEventSubscription(StaticSpaceExpression expression) {
    super(expression);
  }

}
