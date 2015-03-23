package com.verba.language.emit.header.litfile;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.targets.artifacts.BuildSpecificationArtifact;

import java.io.File;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class LitFilePathResolver {
  private final BuildSpecificationArtifact specification;
  private final File path;

  public LitFilePathResolver(Build build) {
    this.specification = build.getArtifactOfType(BuildSpecificationArtifact.class);
    this.path = determinePathFromBuild(specification);
  }

  private File determinePathFromBuild(BuildSpecificationArtifact specification) {
    if (specification.containsOutputPath()) {
      return new File(specification.outputPath());
    } else {
      return null;
    }
  }

  public boolean hasOutputFolderSpecified() { return this.specification.containsOutputPath(); }
  public File path() { return path; }
}
