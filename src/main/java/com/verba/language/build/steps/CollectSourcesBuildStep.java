package com.verba.language.build.steps;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.coordination.BuildProcess;
import com.verba.language.build.coordination.BuildStep;
import com.verba.language.build.processes.BuildAssemblyProcess;
import com.verba.language.build.processes.BuildExportableAssemblyProcess;
import com.verba.tools.files.FileTools;

import java.io.File;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class CollectSourcesBuildStep {
  private static final String V_FILE = ".v";
  private static final Predicate<File> IS_V_FILE = new Predicate<File>() {
    @Override
    public boolean test(File file) {
      return file.getName().endsWith(V_FILE);
    }
  };

  private final String path;
  private final QIterable<File> files;
  public CollectSourcesBuildStep(String path) {
    this.path = path;
    this.files = FileTools.findInSubfolders(path, IS_V_FILE);
  }

  public String path() { return this.path; }
  public QIterable<File> files() { return this.files; }
}
