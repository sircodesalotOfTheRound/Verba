package com.verba.vblz.build.objectfile;

import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.tools.display.ConsoleTools;
import com.verba.tools.files.FileTools;
import com.verba.tools.tasks.Task;

import java.io.IOException;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class SymbolFileCreationTask implements Task {
  private final SourceFilePathInfo sourceFile;

  public SymbolFileCreationTask(SourceFilePathInfo sourceFile) {
    this.sourceFile = sourceFile;
  }

  private VerbaExpression compileFile() {
    return new StaticSpaceExpression(sourceFile);
  }

  private void emitToSymbolFile() throws IOException {
    // If the symbol file is already up to date, don't bother.
    if (!sourceFile.isSymbolFileUpToDate()) {
      VerbaExpression compiledFile = compileFile();

      FileTools.createFolder(sourceFile.outputFolder());
      FileTools.createFile(sourceFile.outputPath());
      FileTools.serializeObjectToFile(sourceFile.outputPath(), compiledFile);

      ConsoleTools.printlnOk("Compiled symbol file: %s from source %s",
        sourceFile.outputPath(), sourceFile.absolutePath());
    }
  }

  @Override
  public void perform() {
    try {
      emitToSymbolFile();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
