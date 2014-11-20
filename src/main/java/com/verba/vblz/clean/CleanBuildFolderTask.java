package com.verba.vblz.clean;

import com.verba.tools.EnvironmentHelpers;
import com.verba.tools.display.ConsoleTools;
import com.verba.tools.tasks.Task;

import java.io.File;

/**
 * Created by sircodesalot on 14/8/31.
 */
public class CleanBuildFolderTask implements Task {
  @Override
  public void perform() {
    File buildFolder = new File(EnvironmentHelpers.getSymbolFolderPath());

    for (File file : buildFolder.listFiles()) {
      file.delete();
    }

    ConsoleTools.printlnOk("Successfully cleaned build folder.");
  }
}
