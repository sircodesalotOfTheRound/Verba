package com.verba.language.graph.events;

import com.verba.language.graph.events.interfaces.VerbaExpressionBuildEventSubscription;
import com.verba.language.parse.expressions.LitFileRootExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class StaticSpaceVerbaExpressionBuildEventSubscription extends VerbaExpressionBuildEventSubscription<LitFileRootExpression> {
  public StaticSpaceVerbaExpressionBuildEventSubscription(LitFileRootExpression expression) {
    super(expression);
  }

}
