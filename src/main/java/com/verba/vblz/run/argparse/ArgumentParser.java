package com.verba.vblz.run.argparse;

import com.verba.tools.tasks.Task;
import com.verba.vblz.build.BuildProjectTask;
import com.verba.vblz.clean.CleanBuildFolderTask;
import com.verba.vblz.newproject.CreateNewProjectTask;
import com.verba.vblz.rebuild.RebuildProjectTask;
import com.verba.vblz.showusage.PrintUsageTask;

/**
 * Created by sircodesalot on 14/8/29.
 */
public class ArgumentParser {
  private final String[] args;

  public ArgumentParser(String[] args) {
    this.args = args;
  }

  public void runApplication() {
    Task task = null;

    if (contains("clean")) {
      task = new CleanBuildFolderTask();
    } else if (contains("new")) {
      task = new CreateNewProjectTask(args);
    } else if (contains("build")) {
      task = new BuildProjectTask();
    } else if (contains("rebuild")) {
      task = new RebuildProjectTask();
    }

    // Perform the task
    if (task == null) {
      task = new PrintUsageTask();
    }

    task.perform();
  }

  private boolean contains(String arg) {
    for (String argument : args) {
      if (argument != null && argument.equalsIgnoreCase(arg)) {
        return true;
      }
    }

    return false;
  }
}
