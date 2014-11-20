package com.verba.vblz.build.buildscript;

/**
 * Created by sircodesalot on 14/9/9.
 */
public class BuildInfo {

  private final String author;
  private final String name;
  private final String version;

  public BuildInfo(String author, String name, String version) {

    this.author = author;
    this.name = name;
    this.version = version;
  }

  public String author() {
    return author;
  }

  public String name() {
    return name;
  }

  public String version() {
    return version;
  }

  @Override
  public String toString() {
    return String.format("%s:%s:%s", author, name, version);
  }
}
