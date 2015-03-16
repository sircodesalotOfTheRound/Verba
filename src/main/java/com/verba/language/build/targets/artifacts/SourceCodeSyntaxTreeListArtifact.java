package com.verba.language.build.targets.artifacts;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class SourceCodeSyntaxTreeListArtifact implements BuildArtifact {
  private final QIterable<VerbaCodePage> syntaxTrees;

  public SourceCodeSyntaxTreeListArtifact(QIterable<VerbaCodePage> syntaxTreeList) {
    this.syntaxTrees = syntaxTreeList;
  }

  public QIterable<VerbaCodePage> syntaxTrees() { return this.syntaxTrees; }
}
