package com.verba.language.build;

/**
 * Created by sircodesalot on 14/12/12.
 */
public class BuildConfiguration {
  private final String buildName;
  private boolean shouldCreateSymbolTable = true;
  private boolean shouldEmitCode = true;
  private boolean isDebugBuild = true;

  public BuildConfiguration() { this("defualt-build"); }
  public BuildConfiguration(String buildName) {
    this.buildName = buildName;
  }

  public String buildName() { return this.buildName; }

  public boolean createSymbolTable() { return this.shouldCreateSymbolTable; }
  public BuildConfiguration shouldCreateSymbolTable(boolean createSymbolTable) {
    this.shouldCreateSymbolTable = createSymbolTable;
    return this;
  }

  public boolean emitCode() { return this.shouldEmitCode; }
  public BuildConfiguration shouldEmitCode(boolean emitCode) {
    this.shouldEmitCode = emitCode;
    return this;
  }

  public boolean isDebugBuild() { return this.shouldEmitCode; }
  public BuildConfiguration isDebugBuild(boolean emitCode) {
    this.shouldEmitCode = emitCode;
    return this;
  }
}
