package com.verba.language.build.targets.artifacts;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class LitFileSyntaxTreeArtifact implements BuildArtifact {
  private final LitFileRootExpression expression;
  public LitFileSyntaxTreeArtifact(QIterable<VerbaSourceCodeFile> sourcePages) {
    this.expression = new LitFileRootExpression(sourcePages);
  }

  public LitFileRootExpression expression() { return this.expression; }
}
