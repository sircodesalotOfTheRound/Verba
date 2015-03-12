package com.verba.language.build.configuration;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.coordination.BuildManager;
import com.verba.language.build.source.CodeUnit;

/**
 * Created by sircodesalot on 14/12/12.
 */
public class BuildSpecification {
  private final String buildName;
  private boolean shouldCreateSymbolTable = true;
  private boolean shouldEmitCode = true;
  private boolean isDebugBuild = true;
  private QList<String> sourceFolders = new QList<>();

  private QList<CodeUnit> codeUnits = new QList<>();


  public BuildSpecification() { this("defualt-build"); }
  public BuildSpecification(String buildName) {
    this.buildName = buildName;
  }

  public String buildName() { return this.buildName; }

  public boolean createSymbolTable() { return this.shouldCreateSymbolTable; }
  public BuildSpecification shouldCreateSymbolTable(boolean createSymbolTable) {
    this.shouldCreateSymbolTable = createSymbolTable;
    return this;
  }

  public boolean emitCode() { return this.shouldEmitCode; }
  public BuildSpecification shouldEmitCode(boolean emitCode) {
    this.shouldEmitCode = emitCode;
    return this;
  }

  public boolean isDebugBuild() { return this.shouldEmitCode; }
  public BuildSpecification isDebugBuild(boolean emitCode) {
    this.shouldEmitCode = emitCode;
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

  public BuildConfiguration configuration() {
    return new BuildConfiguration(this);
  }

  public BuildManager generateBuild() {
    Build build = new Build(this);
    return new BuildManager(build);
  }
}
