package com.verba.language.build.steps;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.coordination.BuildStep;
import com.verba.language.build.info.BuildInfoItem;
import com.verba.language.build.infoitems.SourcesBuildItem;
import com.verba.tools.files.FileTools;

import java.io.File;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class CollectSourcesBuildStep extends BuildStep {
  private static final String V_FILE = ".v";
  private static final Predicate<File> IS_V_FILE = new Predicate<File>() {
    @Override
    public boolean test(File file) {
      return file.getName().endsWith(V_FILE);
    }
  };

  public CollectSourcesBuildStep(Build build) {
    this.addSourcesToBuild(build);
  }

  private void addSourcesToBuild(Build build) {
    QIterable<File> files = build.configuration()
      .sourceFolders()
      .flatten(path -> FileTools.findInSubfolders(path, IS_V_FILE)).toSet();

    SourcesBuildItem sourcesBuildItem = new SourcesBuildItem(files);
    this.addBuildItem(sourcesBuildItem);
  }
}
