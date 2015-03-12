package com.verba.language.build.coordination;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.info.BuildInfoItem;

/**
 * Created by sircodesalot on 15/3/3.
 */
public abstract class BuildStep {
  private final QList<BuildInfoItem> buildItems = new QList<>();

  protected void addBuildItem(BuildInfoItem item) {
    this.buildItems.add(item);
  }

  public QIterable<BuildInfoItem> buildItems() {
    return this.buildItems;
  }
}
