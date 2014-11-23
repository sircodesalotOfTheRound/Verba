package com.verba.language.emit.images;

/**
 * Created by sircodesalot on 14/9/19.
 */
public abstract class ImageSegment {

  public enum ImageSegmentType {
    FUNCTION,
    STRING_TABLE
  }

  private final ImageSegmentType type;

  public ImageSegment(ImageSegmentType type) {
    this.type = type;
  }

  public ImageSegmentType type() { return this.type; }
  public abstract Iterable<Byte> data();
}
