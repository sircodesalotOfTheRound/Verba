package com.verba.language.emit.header.litfile;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.emit.images.types.common.FilePersistenceWriter;
import com.verba.language.emit.litfile.LitFileItem;
import com.verba.tools.exceptions.CompilerException;

import java.io.File;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class LitFileHeader implements LitFileItem {
  private final LitFilePathResolver path;
  private final StringTableArtifact stringTable;

  public LitFileHeader(Build build) {
    this.path = this.validatePath(build);
    this.stringTable = captureStringTable(build);
  }

  private StringTableArtifact captureStringTable(Build build) {
    if (build.containsArtifactOfType(StringTableArtifact.class)) {
      return build.getArtifactOfType(StringTableArtifact.class);
    } else {
      throw new CompilerException("Lit file must contain string table");
    }
  }

  private LitFilePathResolver validatePath(Build build) {
    LitFilePathResolver pathResolver = new LitFilePathResolver(build);
    if (pathResolver.hasOutputFolderSpecified()) {
      return pathResolver;
    } else {
      throw new CompilerException("Lit file must have an output path");
    }
  }

  public File path() { return path.path(); }


  @Override
  public void emit(FilePersistenceWriter image) {
    image.writeRawString("file-type", "vlit-file");
  }
}
