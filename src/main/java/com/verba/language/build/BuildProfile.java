package com.verba.language.build;

import com.verba.language.emit.header.StringTable;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class BuildProfile {
  private final boolean isDebugBuild;
  private final StringTable stringTable;

  public BuildProfile() { this(false); }

  public BuildProfile(boolean isDebugBuild) {
    this.isDebugBuild = isDebugBuild;
    this.stringTable = new StringTable();
  }

  public boolean isDebugBuild() { return this.isDebugBuild; }
  public StringTable stringTable() { return this.stringTable; }
}
