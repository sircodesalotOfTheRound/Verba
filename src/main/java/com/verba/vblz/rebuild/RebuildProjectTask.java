package com.verba.vblz.rebuild;

import com.verba.tools.tasks.Task;
import com.verba.vblz.build.BuildProjectTask;
import com.verba.vblz.clean.CleanBuildFolderTask;

/**
 * Created by sircodesalot on 14/8/31.
 */
public class RebuildProjectTask implements Task {
  @Override
  public void perform() {
    CleanBuildFolderTask cleanTask = new CleanBuildFolderTask();
    BuildProjectTask buildtask = new BuildProjectTask();

    cleanTask.perform();
    buildtask.perform();
  }
}
