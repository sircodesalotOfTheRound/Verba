package com.verba.testtools.display;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class StringTools {
  public static String formatIndented(int indentLevel, String format, Object... args) {
    StringBuilder padding = new StringBuilder();

    for (int index = 0; index != indentLevel; index++) {
      padding.append(" ");
    }

    padding.append("%s");

    String initialFormat = String.format(format, args);
    return String.format(padding.toString(), initialFormat);
  }

  public static String emptyString() {
    return "";
  }
}
