package com.verba.language.build;

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
