package com.verba.language.emit.litfile;

import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.FunctionObjectImageListArtifact;
import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.emit.header.litfile.LitFileHeader;
import com.verba.language.emit.header.litfile.LitFilePathResolver;
import com.verba.language.emit.images.types.common.FilePersistenceWriter;
import com.verba.tools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 15/3/23.
 */
public class LitFileContent {
  private final LitFileHeader header;
  private final StringTableArtifact stringTable;
  private final FunctionObjectImageListArtifact functions;

  public LitFileContent(Build build) {
    this.header = generateHeader(build);
    this.stringTable = build.getArtifactOfType(StringTableArtifact.class);
    this.functions = build.getArtifactOfType(FunctionObjectImageListArtifact.class);
  }

  private LitFileHeader generateHeader(Build build) {
    return new LitFileHeader(build);
  }

  public static boolean isBuildInValidStateToGenerateLitFile(Build build) {
    LitFilePathResolver pathResolver = new LitFilePathResolver(build);
    return (pathResolver.hasOutputFolderSpecified());
  }

  public void save() {
    try (FilePersistenceWriter writer = new FilePersistenceWriter(header.path())) {
      header.emit(writer);
      functions.emit(writer);

    } catch (Exception e) {
      throw new CompilerException("Unable to persist file");
    }
  }

}
