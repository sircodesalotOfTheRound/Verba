package com.verba.language.build.configuration;

import com.verba.language.emit.header.StringTable;

/**
 * Created by sircodesalot on 14/11/23.
 */
@Deprecated
public class BuildProfile {
  private final StringTable stringTable;
  private final BuildSpecification configuration;

  public BuildProfile(BuildSpecification configuration) {
    this.configuration = configuration;
    this.stringTable = new StringTable();
  }

  public boolean isDebugBuild() { return this.configuration.isDebugBuild(); }
  public boolean shouldEmitCode() { return this.configuration.emitCode(); }
  public boolean shouldCreateSymbolTable() { return this.configuration.createSymbolTable(); }
  public StringTable stringTable() { return this.stringTable; }
}
