package com.verba.language.build.targets.artifacts;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class SourceCodeSyntaxTreeListArtifact implements BuildArtifact {
  private final QIterable<VerbaSourceCodeFile> syntaxTrees;

  public SourceCodeSyntaxTreeListArtifact(QIterable<VerbaSourceCodeFile> syntaxTreeList) {
    this.syntaxTrees = syntaxTreeList;
  }

  public QIterable<VerbaSourceCodeFile> syntaxTrees() { return this.syntaxTrees; }
}
