package com.verba.language.build.processes;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.coordination.BuildProcess;
import com.verba.language.build.coordination.BuildStep;
import com.verba.language.build.steps.CollectSourcesBuildStep;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class BuildExportableAssemblyProcess extends BuildProcess {
  public BuildExportableAssemblyProcess(Build build) {
    super(build);
  }

  public void process() {
    BuildStep buildStep = new CollectSourcesBuildStep(build);
    build.addBuildInfo(buildStep.buildItems());
  }
}
