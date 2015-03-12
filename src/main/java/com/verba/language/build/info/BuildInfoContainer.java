package com.verba.language.build.info;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/3/12.
 */
public interface BuildInfoContainer {
  public <T extends BuildInfoItem> boolean containsBuildInfoOfType(Class<T> type);
  public QIterable<Class> buildInfoKeys();
  public void addBuildInfo(BuildInfoItem value);
  public <T extends BuildInfoItem> T getBuildInfo(Class<T> type);

  default public void addBuildInfo(QIterable<BuildInfoItem> buildInfoItems) {
    for (BuildInfoItem item : buildInfoItems) {
      this.addBuildInfo(item);
    }
  }
}
