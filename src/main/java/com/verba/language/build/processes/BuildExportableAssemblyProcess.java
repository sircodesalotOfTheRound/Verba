package com.verba.language.build.processes;

import com.verba.language.build.steps.CollectSourcesBuildStep;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class BuildExportableAssemblyProcess extends BuildAssemblyProcess {
  private final CollectSourcesBuildStep sources;
  public BuildExportableAssemblyProcess(String sourcePath) {
    this.sources = new CollectSourcesBuildStep(sourcePath);
  }

  public CollectSourcesBuildStep sources() { return this.sources; }
}
