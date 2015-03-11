package com.verba.language.build.steps;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.configuration.BuildConfiguration;
import com.verba.language.build.coordination.BuildStep;
import com.verba.language.build.processes.BuildAssemblyProcess;
import com.verba.tools.files.FileTools;

import java.io.File;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class CollectSourcesBuildStep implements BuildStep<BuildAssemblyProcess> {
  private static final String V_FILE = ".v";
  private static final Predicate<File> IS_V_FILE = new Predicate<File>() {
    @Override
    public boolean test(File file) {
      return file.getName().endsWith(V_FILE);
    }
  };

  private QIterable<File> files;

  public CollectSourcesBuildStep(Build build) {
    this.files = this.collectSourcePaths(build);
  }

  private QIterable<File> collectSourcePaths(Build build) {
    return build.configuration()
      .sourceFolders()
      .flatten(path -> FileTools.findInSubfolders(path, IS_V_FILE)).toSet();
  }

  public QIterable<File> files() { return this.files; }
}
