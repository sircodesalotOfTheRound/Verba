package com.verba.language.build.coordination;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.info.BuildInfoContainer;
import com.verba.language.build.info.BuildInfoItem;

/**
 * Created by sircodesalot on 15/3/12.
 */
public class BuildManager implements BuildInfoContainer {
  private final Build build;

  public BuildManager(Build build) {
    this.build = build;
  }

  @Override
  public <T extends BuildInfoItem> boolean containsBuildInfoOfType(Class<T> type) {
    return build.containsBuildInfoOfType(type);
  }

  @Override
  public QIterable<Class> buildInfoKeys() {
    return build.buildInfoKeys();
  }

  @Override
  public void addBuildInfo(BuildInfoItem value) {
    build.addBuildInfo(value);
  }

  @Override
  public <T extends BuildInfoItem> T getBuildInfo(Class<T> type) {
    return build.getBuildInfo(type);
  }
}
