package com.verba.tools.xml;

/**
 * Created by sircodesalot on 14/8/30.
 */
public abstract class XmlNode {
  public abstract String getContentIndented(int indent);

  @Override
  public String toString() {
    return getContentIndented(0);
  }
}
