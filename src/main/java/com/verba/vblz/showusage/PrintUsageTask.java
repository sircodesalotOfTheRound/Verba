package com.verba.vblz.showusage;

import com.verba.tools.display.ConsoleTools;
import com.verba.tools.tasks.Task;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class PrintUsageTask implements Task {

  @Override
  public void perform() {
    System.out.println();
    System.out.println("Useage: vblz <command>");
    System.out.println();
    System.out.println("Commands:");
    System.out.println("    new     : Create a new project schema in this folder.");
    System.out.println("    clean   : Clean out the contents of the build folder.");
    System.out.println("    build   : Build the project located in this folder.");
    System.out.println("    rebuild : Clean and rebuild the project");
    ConsoleTools.printBlankline();

    System.exit(0);
  }
}
