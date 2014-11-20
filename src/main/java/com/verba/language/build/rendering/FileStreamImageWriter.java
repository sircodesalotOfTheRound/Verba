package com.verba.language.build.rendering;

import com.verba.language.build.rendering.functions.MemoryStreamFunctionRenderer;
import com.verba.language.build.rendering.images.ImageRenderer;
import com.verba.language.build.rendering.images.ObjectImage;
import com.verba.language.exceptions.CompilerException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 14/9/26.
 */
public class FileStreamImageWriter implements ImageRenderer, AutoCloseable {
  private final String path;
  private FileOutputStream stream;

  public FileStreamImageWriter(String path) {
    try {
      this.path = path;
      this.stream = new FileOutputStream(path);
    } catch (Exception ex) {
      throw new CompilerException("Unable to load file");
    }
  }

  public String path() {
    return this.path;
  }

  public void save(ObjectImage image) {
    List<ObjectImage> images = new ArrayList<>();
    images.add(image);

    save(images);
  }

  public void save(Iterable<ObjectImage> images) {
    for (ObjectImage image : images) {
      image.accept(this);
    }
  }

  @Override
  public void visit(MemoryStreamFunctionRenderer memoryStream) {
    writeImageDefault(memoryStream);
  }

  private void writeImageDefault(ObjectImage image) {
    byte[] imageTypeCode = image.imageType().asByteArray();
    byte[] data = image.asArray();

    try {
      stream.write(imageTypeCode);
      stream.write(data);
    } catch (IOException ex) {
      throw new CompilerException("Unable to write to file");
    }
  }

  @Override
  public void close() throws Exception {
    this.stream.close();
  }
}
