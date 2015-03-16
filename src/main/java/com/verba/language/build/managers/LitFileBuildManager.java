package com.verba.language.build.managers;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.managers.interfaces.BuildManagerBase;
import com.verba.language.build.events.BuildEventPublisher;
import com.verba.language.build.targets.AddStringTableBuildTarget;
import com.verba.language.build.targets.CollectSourcesBuildTarget;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class LitFileBuildManager extends BuildManagerBase {
  private final BuildEventPublisher publisher;

  public LitFileBuildManager(Build build) {
    super(build);

    this.publisher = new BuildEventPublisher(build)
      .addTarget(new CollectSourcesBuildTarget())
      .addTarget(new AddStringTableBuildTarget());
  }
}
