package com.verba.language.build.processes;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.steps.CollectSourcesBuildStep;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class BuildExportableAssemblyProcess extends BuildAssemblyProcess {
  public BuildExportableAssemblyProcess(Build build) {
    super (build);

    this.attachSources(build);
  }

  private void attachSources(Build build) {
    build.addBuildInfo(new CollectSourcesBuildStep(build));
  }
}
