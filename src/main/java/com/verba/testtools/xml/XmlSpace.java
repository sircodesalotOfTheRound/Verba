package com.verba.testtools.xml;

import com.verba.testtools.display.StringTools;

/**
 * Created by sircodesalot on 14/8/30.
 */
public class XmlSpace extends XmlNode {
  @Override
  public String getContentIndented(int indent) {
    return StringTools.emptyString();
  }
}
