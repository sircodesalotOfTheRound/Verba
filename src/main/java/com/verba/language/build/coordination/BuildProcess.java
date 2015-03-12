package com.verba.language.build.coordination;


import com.verba.language.build.configuration.Build;

/**
 * Created by sircodesalot on 15/3/3.
 */
public abstract class BuildProcess {
  protected final Build build;

  public BuildProcess(Build build) {
    this.build = build;
  }

  protected Build build() { return this.build; }

  protected void runBuildStep(BuildStep step) {
    build.addBuildInfo(step);
  }

  public abstract void process();
}
