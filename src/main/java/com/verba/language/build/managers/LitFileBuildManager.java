package com.verba.language.build.managers;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.managers.interfaces.BuildManagerBase;
import com.verba.language.build.events.BuildEventPublisher;
import com.verba.language.build.targets.*;
import com.verba.language.build.targets.artifacts.BuildSpecificationArtifact;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class LitFileBuildManager extends BuildManagerBase {
  private final BuildEventPublisher publisher;

  public LitFileBuildManager(Build build, BuildSpecification specification) {
    super(build);

    this.publisher = new BuildEventPublisher(build)
      .addTarget(new CollectSourcePathsBuildTarget())
      .addTarget(new AddStringTableBuildTarget())
      .addTarget(new SourceCodeSyntaxTreeListBuildTarget())
      .addTarget(new FunctionObjectCodeBuildTarget())
      .addTarget(new SymbolTableBuildTarget())
      .addTarget(new LitFileBuildTarget())
      .addTarget(new SyntaxTreeNotificationBuildTarget())
      .addTarget(new LitFileSyntaxTreeBuildTarget());

    this.addBuildSpecification(build, specification);
  }

  private void addBuildSpecification(Build build, BuildSpecification specification) {
    BuildSpecificationArtifact artifact = new BuildSpecificationArtifact(specification);
    build.addArtifact(artifact);
  }
}
