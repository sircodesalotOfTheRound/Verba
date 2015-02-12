package com.verba.language.build;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.source.CodeUnit;

/**
 * Created by sircodesalot on 14/12/12.
 */
public class BuildConfiguration {
  private final String buildName;
  private boolean shouldCreateSymbolTable = true;
  private boolean shouldEmitCode = true;
  private boolean isDebugBuild = true;

  private QList<CodeUnit> codeUnits = new QList<>();

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

  public QIterable<CodeUnit> codeUnits(){ return this.codeUnits; }
  public BuildConfiguration addCodeUnit(CodeUnit codeUnit) {
    this.codeUnits.add(codeUnit);
    return this;
  }

  public BuildConfiguration addTranslationUnit(String path) {
    return this.addCodeUnit(CodeUnit.fromFile(path));
  }

  public Build build() {
    Build build = new Build(this);
    build.build();

    return build;
  }
}
