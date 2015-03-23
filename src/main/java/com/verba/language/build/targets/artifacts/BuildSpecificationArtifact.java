package com.verba.language.build.targets.artifacts;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class BuildSpecificationArtifact implements BuildArtifact {
  private final BuildSpecification specification;

  public BuildSpecificationArtifact(BuildSpecification specification) {
    this.specification = specification;
  }

  public String name() { return specification.buildName(); }

  public boolean isDebugBuild() { return specification.isDebugBuild(); }

  public QIterable<String> sourceFolders() { return specification.sourceFolders(); }

  public boolean containsOutputPath() { return specification.containsOutputPath(); }

  public String outputPath() { return specification.outputPath(); }
}
