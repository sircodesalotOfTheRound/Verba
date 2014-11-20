package com.verba.vblz.build.buildscript;

import com.javalinq.interfaces.QIterable;
import com.verba.tools.EnvironmentHelpers;
import com.verba.tools.xml.XmlElement;

/**
 * Created by sircodesalot on 14/9/9.
 */
public class BuildScriptDecorator {
  XmlElement buildScript = EnvironmentHelpers.loadBuildScript();
  private final QIterable<BuildInfo> buildSchemas;

  public BuildScriptDecorator() {
    this.buildSchemas = generateBuildSchemas();
  }

  private QIterable<BuildInfo> generateBuildSchemas() {
    return buildScript
      .childrenByName("build")
      .map(buildElement -> {
        String author = buildElement.elementByName("author").innerText();
        String name = buildElement.elementByName("name").innerText();
        String version = buildElement.elementByName("version").innerText();

        return new BuildInfo(author, name, version);
      })
      .toList();
  }

  public QIterable<BuildInfo> buildSchemas() {
    return this.buildSchemas;
  }

}
