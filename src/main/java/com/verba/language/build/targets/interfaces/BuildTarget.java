package com.verba.language.build.targets.interfaces;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.artifacts.containers.BuildArtifact;

/**
 * Created by sircodesalot on 15/3/3.
 */
public interface BuildTarget {
  public void onActivate(Build build);
  public void onBuildTargetsUpdated(Build build, BuildArtifact artifact);
}
