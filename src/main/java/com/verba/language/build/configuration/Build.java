package com.verba.language.build.configuration;


import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.coordination.BuildProcess;
import com.verba.language.build.processes.BuildExportableAssemblyProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class Build {
  private final BuildConfiguration configuration;
  private final Map<Class, Object> buildInfo = new HashMap<>();

  public Build(BuildSpecification profile) { this(profile.configuration()); }
  public Build(BuildConfiguration configuration) {
    this.configuration = configuration;

    this.runBuildProcess(new BuildExportableAssemblyProcess(this));
  }

  private void runBuildProcess(BuildProcess process) {
    process.process();
  }

  public BuildConfiguration configuration() { return this.configuration; }

  public <T> boolean containsBuildInfoOfType(Class<T> type) {
    return buildInfo.containsKey(type);
  }

  public QIterable<Class> buildInfoKeys() { return new QSet<>(this.buildInfo.keySet()); }

  public void addBuildInfo(Object value) {
    buildInfo.put(value.getClass(), value);
  }

  public <T> T getBuildInfo(Class<T> type) { return (T)this.buildInfo.get(type); }

}
