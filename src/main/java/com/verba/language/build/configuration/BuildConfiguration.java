package com.verba.language.build.configuration;

import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 15/3/4.
 */
public class BuildConfiguration {
  private final BuildSpecification configuration;
  public BuildConfiguration(BuildSpecification configuration) {
    this.configuration = configuration;
  }

  public QIterable<String> sourceFolders() { return this.configuration.sourceFolders(); }
}
