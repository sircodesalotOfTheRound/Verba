package com.verba.language.codegen.images;

/**
 * Created by sircodesalot on 14/9/19.
 */
public abstract class ImageSegment {

  public enum ImageSegmentType {
    FUNCTION
  }

  private final ImageSegmentType type;
  private final Iterable<Byte> data;

  public ImageSegment(ImageSegmentType type, Iterable<Byte> data) {
    this.type = type;
    this.data = data;
  }

  public ImageSegmentType type() { return this.type; }
  public Iterable<Byte> data() { return this.data; }
}
