package com.verba.language.build.nodeevents.subscriptions;

import com.verba.language.build.nodeevents.BuildEventSubscription;
import com.verba.language.parse.expressions.LitFileRootExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class StaticSpaceBuildEventSubscription extends BuildEventSubscription<LitFileRootExpression> {
  public StaticSpaceBuildEventSubscription(LitFileRootExpression expression) {
    super(expression);
  }

}
