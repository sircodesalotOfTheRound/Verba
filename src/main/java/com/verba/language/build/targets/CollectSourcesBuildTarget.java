package com.verba.language.build.targets;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.artifacts.containers.BuildArtifact;
import com.verba.language.build.artifacts.SourcesBuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.tools.files.FileTools;

import java.io.File;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class CollectSourcesBuildTarget implements BuildTarget {
  private static final String V_FILE = ".v";
  private static final Predicate<File> IS_V_FILE = new Predicate<File>() {
    @Override
    public boolean test(File file) {
      return file.getName().endsWith(V_FILE);
    }
  };

  @Override
  public void onActivate(Build build) {
    QIterable<File> files = build.specification()
      .sourceFolders()
      .flatten(path -> FileTools.findInSubfolders(path, IS_V_FILE)).toSet();

    build.addArtifact(new SourcesBuildArtifact(files));
  }

  @Override
  public void onBuildTargetsUpdated(Build build, BuildArtifact target) {

  }
}
