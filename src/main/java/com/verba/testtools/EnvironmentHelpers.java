package com.verba.testtools;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.testtools.files.FileTools;
import com.verba.testtools.xml.XmlElement;

import java.io.*;
import java.util.function.Supplier;

/**
 * Created by sircodesalot on 14/8/29.
 */
public class EnvironmentHelpers {
  private static final File buildRoot = new Supplier<File>() {
    @Override
    public File get() {
      return FileTools
        .findInParentFolders(getCurrentFolderPath(),
          folder -> {
            QIterable<String> filesInFolder = new QList<>(folder.list());
            return filesInFolder.any(fileName -> fileName.equals("vblz-build.xml"));
          })
        .firstOrNull();
    }
  }.get();

  public static String getHostname() {
    try {
      Process process = Runtime.getRuntime().exec("hostname");
      InputStream inputStream = process.getInputStream();
      InputStreamReader streamReader = new InputStreamReader(inputStream);
      BufferedReader reader = new BufferedReader(streamReader);

      return reader.readLine();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return "anonymous";
  }

  public static String getOnlyCurrentFolderName() {
    String path = System.getProperty("user.dir");
    String[] directories = path.split("/");

    return directories[directories.length - 1];
  }

  public static String getCodeFolderPath() {
    return String.format("%s/%s", EnvironmentHelpers.getBuildConfigFolderPath(), "code");
  }

  public static String getSymbolFolderPath() {
    return String.format("%s/%s/%s", EnvironmentHelpers.getBuildConfigFolderPath(), "build", "symbols");
  }

  public static String getCurrentFolderPath() {
    return System.getProperty("user.dir");
  }

  public static String getBuildConfigFolderPath() {
    return buildRoot.getAbsolutePath();
  }

  public static XmlElement loadBuildScript() {
    String buildScriptPath = String.format("%s/%s", getBuildConfigFolderPath(), "vblz-build.xml");
    String content = FileTools.readAllText(buildScriptPath);

    return XmlElement.parse(content);
  }
}
