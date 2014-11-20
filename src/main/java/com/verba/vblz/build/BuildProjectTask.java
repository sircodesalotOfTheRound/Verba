package com.verba.vblz.build;

import com.verba.tools.display.ConsoleTools;
import com.verba.tools.tasks.Task;
import com.verba.vblz.build.buildscript.BuildScriptDecorator;
import com.verba.vblz.build.objectfile.SourceFilePathInfo;
import com.verba.vblz.build.objectfile.SymbolFileCreationTask;
import com.verba.vblz.build.subtasks.CreateCompilationListTask;
import com.verba.vblz.build.symboltable.MergeSymbolTableTask;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class BuildProjectTask implements Task {
  private final CreateCompilationListTask createCompilationListTask = new CreateCompilationListTask();

  private void createSymbolFiles(Iterable<SourceFilePathInfo> sourceFiles) {
    for (SourceFilePathInfo sourceFile : sourceFiles) {
      SymbolFileCreationTask createSymbolFileTask = new SymbolFileCreationTask(sourceFile);
      createSymbolFileTask.perform();
    }
  }

  private void mergeSymbolTables() {
    MergeSymbolTableTask task = new MergeSymbolTableTask();
    task.perform();

    ConsoleTools.printlnOk("Successfully linked files into symbol table.");
  }

  @Override
  public void perform() {
    createCompilationListTask.perform();
    ConsoleTools.printlnOk("Generate list of files to build.");

    createSymbolFiles(createCompilationListTask.files());
    ConsoleTools.printlnOk("Successfully built symbol files.");

    mergeSymbolTables();

    ConsoleTools.printlnOk("Successfully built: %s", new BuildScriptDecorator().buildSchemas().first());
  }
}
