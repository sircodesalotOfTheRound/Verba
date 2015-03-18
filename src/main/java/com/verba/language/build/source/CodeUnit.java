package com.verba.language.build.source;

import com.verba.tools.files.FileTools;

/**
 * Created by sircodesalot on 15/2/10.
 */
public interface CodeUnit {
  public abstract String path();
  public abstract String content();
  public abstract String hash();

  public static CodeUnit fromFile(String path) {
    String content = FileTools.readAllText(path);
    return new SourceCodeUnit(path, content);
  }
}
