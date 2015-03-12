package com.verba.language.build.info;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/3/12.
 */
public interface BuildInfoContainer {
  public <T> boolean containsBuildInfoOfType(Class<T> type);
  public QIterable<Class> buildInfoKeys();
  public void addBuildInfo(Object value);
  public <T> T getBuildInfo(Class<T> type);
}
