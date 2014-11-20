package com.verba.vblz.build.objectfile;

import com.verba.tools.EnvironmentHelpers;
import com.verba.tools.files.FileTools;

import java.io.File;
import java.nio.file.attribute.FileTime;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class SourceFilePathInfo {
  private final String absolutePath;
  private final String relativeFolder;
  private final String buildPath;
  private final String codePath;
  private final String filename;
  private final String filenameWithoutExtension;
  private final String outputFolder;
  private final String outputPath;

  public SourceFilePathInfo(String absolutePath) {
    this.absolutePath = absolutePath;
    this.buildPath = determineBuildPath();
    this.codePath = determineCodePath();
    this.relativeFolder = determineRelativeFolder(absolutePath);
    this.filename = determineFilename(absolutePath);
    this.filenameWithoutExtension = determineFilenameWithoutExtension(absolutePath);
    this.outputFolder = determineOutputFolder(absolutePath);
    this.outputPath = determineOutputPath(absolutePath);
  }

  private String determineOutputFolder(String absolutePath) {
    String relativePath = this.determineRelativeFolder(absolutePath);

    return String.format("%s/build/symbols/%s", buildPath, relativePath);
  }

  private String determineCodePath() {
    return String.format("%s/%s", EnvironmentHelpers.getBuildConfigFolderPath(), "code");
  }

  private String determineFilenameWithoutExtension(String absolutePath) {
    String filename = this.determineFilename(absolutePath);

    int lastIndexOfVExtension = filename.lastIndexOf(".v");
    return filename.substring(0, lastIndexOfVExtension);
  }

  private String determineOutputPath(String absolutePath) {
    String filenameWithoutExtension = this.determineFilenameWithoutExtension(absolutePath);
    String outputFolder = this.determineOutputFolder(absolutePath);

    return String.format("%s%s.sym", outputFolder, filenameWithoutExtension);
  }

  public SourceFilePathInfo(File file) {
    this(file.getAbsolutePath());
  }

  private String determineFilename(String absolutePath) {
    int lastFolderSlashIndex = absolutePath.lastIndexOf("/");

    // Plus '1' to move past the slash.
    return absolutePath.substring(lastFolderSlashIndex + 1);
  }

  private String determineRelativeFolder(String absolutePath) {
    String relativePath = absolutePath.replace(this.codePath, "");

    int indexOfLastSlash = relativePath.lastIndexOf("/");

    if (indexOfLastSlash > 1) {
      return relativePath.substring(1, indexOfLastSlash + 1);
    } else {
      return "";
    }
  }

  private String determineBuildPath() {
    return EnvironmentHelpers.getBuildConfigFolderPath();
  }

  public String absolutePath() {
    return this.absolutePath;
  }

  public String relativePath() {
    return this.relativeFolder;
  }

  public String buildPath() {
    return this.buildPath;
  }

  public String filename() {
    return this.filename;
  }

  public String filenameWithoutExtension() {
    return this.filenameWithoutExtension;
  }

  public String outputFolder() {
    return this.outputFolder;
  }

  public String outputPath() {
    return this.outputPath;
  }

  public boolean isSymbolFileUpToDate() {
    // If the file doesn't exist, can't be up to date.
    if (!FileTools.fileExists(this.outputPath)) {
      return false;
    }

    FileTime sourceFileModifiedTime = FileTools.getModifiedTime(this.absolutePath);
    FileTime symbolFileModifiedTime = FileTools.getModifiedTime(this.outputPath);

    // Source file should be modified less than the symbol file.
    return sourceFileModifiedTime.compareTo(symbolFileModifiedTime) < 0;
  }

  @Override
  public String toString() {
    return this.absolutePath;
  }
}
