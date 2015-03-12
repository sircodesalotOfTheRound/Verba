package com.verba.language.build.infoitems;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.info.BuildInfoItem;

import java.io.File;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class SourcesBuildItem implements BuildInfoItem {
  private final QIterable<File> files;
  public SourcesBuildItem(QIterable<File> files) {
    this.files = files;
  }

  public QIterable<File> files() { return this.files; }
}
