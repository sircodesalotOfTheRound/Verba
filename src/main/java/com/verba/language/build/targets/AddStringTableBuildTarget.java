package com.verba.language.build.targets;

import com.verba.language.build.artifacts.StringTableBuildArtifact;
import com.verba.language.build.artifacts.containers.BuildArtifact;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.interfaces.BuildTarget;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class AddStringTableBuildTarget implements BuildTarget {
  @Override
  public void onActivate(Build build) {
    build.addArtifact(new StringTableBuildArtifact());
  }

  @Override
  public void onBuildTargetsUpdated(Build build, BuildArtifact artifact) {

  }
}
