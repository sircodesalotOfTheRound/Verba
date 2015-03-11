package com.verba.language.build.processes;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.coordination.BuildProcess;
import com.verba.language.build.steps.CollectSourcesBuildStep;
import com.verba.language.build.steps.ParseSyntaxTreeBuildStep;

/**
 * Created by sircodesalot on 15/3/4.
 */
public abstract class BuildAssemblyProcess implements BuildProcess {
  private final Build build;

  public BuildAssemblyProcess(Build build) {
    this.build = build;
  }

  public Build build() { return this.build; }
}
