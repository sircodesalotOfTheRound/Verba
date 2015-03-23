package com.verba.language.build.configuration;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.managers.LitFileBuildManager;
import com.verba.language.build.source.CodeUnit;

/**
 * Created by sircodesalot on 14/12/12.
 */
public class BuildSpecification {
  private final String buildName;
  private boolean shouldCreateSymbolTable = true;
  private boolean shouldPersist = true;
  private boolean isDebugBuild = true;
  private QList<String> sourceFolders = new QList<>();

  private QList<CodeUnit> codeUnits = new QList<>();


  public BuildSpecification() { this("default-build"); }
  public BuildSpecification(String buildName) {
    this.buildName = buildName;
  }

  public String buildName() { return this.buildName; }

  public boolean createSymbolTable() { return this.shouldCreateSymbolTable; }
  public BuildSpecification shouldCreateSymbolTable(boolean createSymbolTable) {
    this.shouldCreateSymbolTable = createSymbolTable;
    return this;
  }

  private String outputPath;
  public boolean containsOutputPath() { return this.outputPath != null; }
  public String outputPath() { return this.outputPath; }
  public BuildSpecification outputPath(String path) {
    this.outputPath = path;
    return this;
  }

  public boolean isDebugBuild() { return this.isDebugBuild; }
  public BuildSpecification isDebugBuild(boolean isDebugBuild) {
    this.isDebugBuild = isDebugBuild;
    return this;
  }

  public QIterable<String> sourceFolders() { return this.sourceFolders; }
  public BuildSpecification addSourceFolder(String sourceFolder) {
    this.sourceFolders.add(sourceFolder);
    return this;
  }

  public QIterable<CodeUnit> codeUnits(){ return this.codeUnits; }
  public BuildSpecification addCodeUnit(CodeUnit codeUnit) {
    this.codeUnits.add(codeUnit);
    return this;
  }

  public BuildSpecification addTranslationUnit(String path) {
    return this.addCodeUnit(CodeUnit.fromFile(path));
  }

  public LitFileBuildManager createLitFileBuild() {
    Build build = new Build(this);
    return new LitFileBuildManager(build, this);
  }
}
