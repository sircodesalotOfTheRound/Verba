package com.verba.language.build.targets;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.artifacts.SourceCodePathListArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.testtools.files.FileTools;

import java.io.File;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class CollectSourcePathsBuildTarget extends BuildTarget {
  private static final String V_FILE = ".v";
  private static final Predicate<File> IS_V_FILE = new Predicate<File>() {
    @Override
    public boolean test(File file) {
      return file.getName().endsWith(V_FILE);
    }
  };

  @Override
  public void onBuildUpdated(Build build, BuildArtifact target) {
    if (!build.containsArtifactOfType(SourceCodePathListArtifact.class)) {
      QIterable<File> files = build.specification()
        .sourceFolders()
        .flatten(path -> FileTools.findInSubfolders(path, IS_V_FILE)).toSet();

      build.addArtifact(new SourceCodePathListArtifact(files));
    }
  }
}
