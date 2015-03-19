package com.verba.language.build.targets;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.LitFileSyntaxTreeArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.language.graph.expressions.events.interfaces.VerbaExpressionBuildEvent;
import com.verba.language.graph.expressions.events.interfaces.VerbaExpressionBuildEventSubscriptionBase;
import com.verba.language.parse.expressions.VerbaExpression;

/**
 * Notify the syntax tree that certain build resources are available.
 */
public class SyntaxNotificationBuildTarget extends BuildTarget {
  public SyntaxNotificationBuildTarget() {
    super(LitFileSyntaxTreeArtifact.class, SymbolTableArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (artifact instanceof LitFileSyntaxTreeArtifact) {
      this.notifyExpressionsParseComplete(build, (LitFileSyntaxTreeArtifact) artifact);
    } else {

    }
  }

  private void notifyExpressionsParseComplete(Build build, LitFileSyntaxTreeArtifact syntaxTree) {
    for (VerbaExpression expression : syntaxTree.expression().allExpressions()) {
      expression.afterContentsParsed(build);
    }
  }


}
