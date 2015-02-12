package com.verba.language.build.event.subscriptions;

import com.verba.language.build.event.BuildEventSubscription;
import com.verba.language.parse.expressions.LitFileRootExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class StaticSpaceBuildEventSubscription extends BuildEventSubscription<LitFileRootExpression> {
  public StaticSpaceBuildEventSubscription(LitFileRootExpression expression) {
    super(expression);
  }

}
