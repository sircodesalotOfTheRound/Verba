package com.verba.language.build.targets.artifacts;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.SymbolsBySourceFileBuildTarget;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolTableBySourceCodePageArtifact implements BuildArtifact {
  private final QIterable<SymbolsBySourceFileBuildTarget.SourceCodeInfo> symbolTables;

  public SymbolTableBySourceCodePageArtifact(QIterable<SymbolsBySourceFileBuildTarget.SourceCodeInfo> symbolTables) {
    this.symbolTables = symbolTables;
  }

  public QIterable<SymbolsBySourceFileBuildTarget.SourceCodeInfo> symbolTables() { return this.symbolTables; }
}
