package com.verba.language.emit.images.interfaces;

/**
 * Created by sircodesalot on 14/12/5.
 */
public class ObjectImageBase implements ObjectImage {
  private ObjectImage image;

  public ObjectImageBase(ObjectImage image) {
    this.image = image;
  }

  @Override
  public String name() { return image.name(); }

  @Override
  public ImageType imageType() { return image.imageType(); }

  @Override
  public long size() { return image.size(); }

  @Override
  public byte[] data() { return image.data(); }
}
