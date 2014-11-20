package com.verba.tools.display;

/**
 * Created by sircodesalot on 14/8/29.
 */
public class ConsoleTools {
  public static void printBlankline() {
    System.out.println();
  }

  public static void println(String format, Object... args) {
    String formattedString = String.format(format, args);
    System.out.println(formattedString);
  }

  public static void printlnOk(String format, Object... args) {
    String formattedString = String.format(format, args);
    println("[OK]\t%s", formattedString);
  }

  public static void printlnIndented(int indentLevel, String format, Object... args) {
    String string = StringTools.formatIndented(indentLevel, format, args);
    System.out.println(string);
  }
}
