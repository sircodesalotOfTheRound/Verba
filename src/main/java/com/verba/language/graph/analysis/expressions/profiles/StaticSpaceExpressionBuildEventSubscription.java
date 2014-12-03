package com.verba.language.graph.analysis.expressions.profiles;

import com.verba.language.graph.analysis.expressions.tools.ExpressionBuildEventSubscription;
import com.verba.language.parsing.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class StaticSpaceExpressionBuildEventSubscription extends ExpressionBuildEventSubscription<StaticSpaceExpression> {
  public StaticSpaceExpressionBuildEventSubscription(StaticSpaceExpression expression) {
    super(expression);
  }

}
