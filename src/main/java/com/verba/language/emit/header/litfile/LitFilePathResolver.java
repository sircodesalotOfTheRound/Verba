package com.verba.language.emit.header.litfile;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.BuildSpecificationArtifact;

import java.io.File;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class LitFilePathResolver {
  private final boolean hasOutputFolderSpecified;
  private final File path;

  public LitFilePathResolver(Build build) {
    this.hasOutputFolderSpecified = determineBuildHasOutputFolderSpecified(build);
    this.path = determinePathFromBuild(build);
  }

  private File determinePathFromBuild(Build build) {
    if (hasOutputFolderSpecified) {
      BuildSpecificationArtifact specification = build.getArtifactOfType(BuildSpecificationArtifact.class);
      return generateOutputPath(specification);
    } else {
      return null;
    }
  }

  private File generateOutputPath(BuildSpecificationArtifact specification) {
    String outputFolder = specification.litfileOutputFolder();
    String binaryName = specification.name();
    if (!binaryName.endsWith(".vlit")) {
      binaryName += ".vlit";
    }

    return new File(outputFolder, binaryName);
  }

  private boolean determineBuildHasOutputFolderSpecified(Build build) {
    BuildSpecificationArtifact specification = build.getArtifactOfType(BuildSpecificationArtifact.class);
    return specification.containsLitfileOutputFolder();
  }

  public boolean hasOutputFolderSpecified() { return this.hasOutputFolderSpecified; }
  public File path() { return path; }
}
