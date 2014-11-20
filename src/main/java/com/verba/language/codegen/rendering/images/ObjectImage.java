package com.verba.language.codegen.rendering.images;

/**
 * Created by sircodesalot on 14/9/26.
 */
public interface ObjectImage {
  void accept(ImageRenderer renderer);

  ImageType imageType();
  byte[] asArray();
}
