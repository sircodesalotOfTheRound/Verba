package com.verba.language.build.configuration;


import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.coordination.BuildProcess;
import com.verba.language.build.info.BuildInfo;
import com.verba.language.build.info.BuildInfoContainer;
import com.verba.language.build.processes.BuildExportableAssemblyProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class Build implements BuildInfoContainer {
  private final BuildConfiguration configuration;
  private final BuildInfo buildInfo = new BuildInfo();

  public Build(BuildSpecification profile) { this(profile.configuration()); }
  public Build(BuildConfiguration configuration) {
    this.configuration = configuration;
    this.runBuildProcess(new BuildExportableAssemblyProcess(this));
  }

  private void runBuildProcess(BuildProcess process) {
    process.process();
  }

  public BuildConfiguration configuration() { return this.configuration; }

  @Override
  public <T> boolean containsBuildInfoOfType(Class<T> type) {
    return buildInfo.containsBuildInfoOfType(type);
  }

  @Override
  public QIterable<Class> buildInfoKeys() {
    return buildInfo.buildInfoKeys();
  }

  @Override
  public void addBuildInfo(Object value) {
    buildInfo.addBuildInfo(value);
  }

  @Override
  public <T> T getBuildInfo(Class<T> type) {
    return buildInfo.getBuildInfo(type);
  }
}
