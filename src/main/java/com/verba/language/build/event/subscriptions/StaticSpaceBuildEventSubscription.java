package com.verba.language.build.event.subscriptions;

import com.verba.language.build.event.BuildEventSubscription;
import com.verba.language.parse.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class StaticSpaceBuildEventSubscription extends BuildEventSubscription<StaticSpaceExpression> {
  public StaticSpaceBuildEventSubscription(StaticSpaceExpression expression) {
    super(expression);
  }

}
