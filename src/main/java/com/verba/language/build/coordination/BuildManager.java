package com.verba.language.build.coordination;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.info.BuildInfoContainer;

/**
 * Created by sircodesalot on 15/3/12.
 */
public class BuildManager implements BuildInfoContainer {
  private final Build build;

  public BuildManager(Build build) {
    this.build = build;
  }

  @Override
  public <T> boolean containsBuildInfoOfType(Class<T> type) {
    return build.containsBuildInfoOfType(type);
  }

  @Override
  public QIterable<Class> buildInfoKeys() {
    return build.buildInfoKeys();
  }

  @Override
  public void addBuildInfo(Object value) {
    build.addBuildInfo(value);
  }

  @Override
  public <T> T getBuildInfo(Class<T> type) {
    return build.getBuildInfo(type);
  }
}
