package com.verba.language.build.targets.artifacts;

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

  public BuildSpecification specification() { return this.specification; }
}
