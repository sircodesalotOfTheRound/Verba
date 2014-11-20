package com.verba.vblz.build.subtasks;

import com.javalinq.interfaces.QIterable;
import com.verba.tools.EnvironmentHelpers;
import com.verba.tools.files.FileTools;
import com.verba.tools.tasks.Task;
import com.verba.vblz.build.objectfile.SourceFilePathInfo;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class CreateCompilationListTask implements Task {
  private QIterable<SourceFilePathInfo> files;

  public QIterable<SourceFilePathInfo> listFiles() {
    return getVerbaCompilableFiles();
  }

  private QIterable<SourceFilePathInfo> getVerbaCompilableFiles() {
    return FileTools
      .findInSubfolders(EnvironmentHelpers.getCodeFolderPath(), file -> file.getAbsolutePath().endsWith(".v"))
      .map(SourceFilePathInfo::new);
  }

  @Override
  public void perform() {
    this.files = listFiles();
  }

  public QIterable<SourceFilePathInfo> files() {
    return this.files;
  }
}
