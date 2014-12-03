package com.verba.language.graph.analysis.expressions.tools;

import com.javalinq.implementations.QList;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.emit.header.StringTable;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class BuildAnalysis {
  private final boolean isDebugBuild;

  public BuildAnalysis() { this(false); }

  public BuildAnalysis(boolean isDebugBuild) {
    this.isDebugBuild = isDebugBuild;
  }

  public boolean isDebugBuild() { return this.isDebugBuild; }
}
