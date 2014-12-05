package com.verba.language.emit.images.interfaces;


/**
 * Created by sircodesalot on 14/9/26.
 */
public interface ObjectImage {
  String name();
  ImageType imageType();

  long size();
  byte[] data();
}
