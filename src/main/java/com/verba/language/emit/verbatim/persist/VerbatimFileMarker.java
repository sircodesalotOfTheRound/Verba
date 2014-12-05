package com.verba.language.emit.verbatim.persist;

/**
 * File markers are used to set waypoints within a file so that they can be used again later.
 */
public class VerbatimFileMarker {
  private final String name;
  private final long offset;

  public VerbatimFileMarker(String name, long offset) {
    this.name = name;
    this.offset = offset;
  }

  public String name() { return this.name; }
  public long offset() { return this.offset; }
}
