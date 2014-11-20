package com.verba.tools.files;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 14/8/31.
 */
public class FileTools {
  public class FileException extends RuntimeException {
    public FileException(String message, Object... format) {
      this(null, message, format);

    }

    public FileException(Exception innerExcetpion, String message, Object... format) {
      super(String.format(message, format), innerExcetpion);
    }
  }

  public static void createFolder(String path) {
    File directory = new File(path);
    directory.mkdirs();
  }

  public static boolean fileExists(String path) {
    return new File(path).exists();
  }

  public static FileTime getModifiedTime(String path) {
    if (fileExists(path)) {
      try {
        Path fileInfo = new File(path).toPath();
        BasicFileAttributes fileAttributes = Files.readAttributes(fileInfo, BasicFileAttributes.class);

        return fileAttributes.lastModifiedTime();

      } catch (Exception ex) {
        throw new FileTools().new FileException(ex, "Unable to check last modify time");
      }
    }

    throw new FileTools().new FileException("Unable to check last modify time, file does not exist");
  }

  public static void createFile(String path) {
    try {
      File directory = new File(path);
      directory.createNewFile();

    } catch (Exception ex) {
      throw new FileTools().new FileException(ex, "Unable to create new file");
    }
  }

  public static void writeAllText(String path, String text) {
    try {
      File file = new File(path);
      FileOutputStream outputStream = new FileOutputStream(file);
      outputStream.write(text.getBytes(), 0, text.length());
      outputStream.flush();
      outputStream.close();

    } catch (Exception ex) {
      throw new FileTools().new FileException(ex, "Unable to write text to file");
    }
  }

  public static String readAllText(String path) {
    try {
      File file = new File(path);
      FileInputStream inputStream = new FileInputStream(file);
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      StringBuilder textResult = new StringBuilder();

      String input = null;
      while ((input = bufferedReader.readLine()) != null) {
        textResult.append(input);
      }

      inputStream.close();
      return textResult.toString();

    } catch (Exception ex) {
      throw new FileTools().new FileException(ex, "Unable to write text to file");
    }
  }


  public static void serializeObjectToFile(String path, Object object) {
    try {
      File file = new File(path);
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      ObjectOutputStream serializer = new ObjectOutputStream(fileOutputStream);
      serializer.writeObject(object);

      serializer.flush();
      serializer.close();

    } catch (Exception ex) {
      ex.printStackTrace();
      throw new FileTools().new FileException(ex, "Unable to serialize object.");
    }
  }

  public static <T> T deserializeObjectFromFile(String path) {
    try {
      File file = new File(path);
      FileInputStream inputStream = new FileInputStream(file);
      ObjectInputStream serializer = new ObjectInputStream(inputStream);

      T object = (T) serializer.readObject();
      inputStream.close();

      return object;

    } catch (Exception ex) {
      throw new FileTools().new FileException(ex, "Unable to write text to file");
    }
  }

  private static QIterable<File> getSubfolders(File directory, QList<File> resultList) {
    File[] files = directory.listFiles();
    resultList.add(directory);

    for (File file : files) {
      if (file.isDirectory()) {
        getSubfolders(file, resultList);
      }
    }

    return resultList;
  }

  public static QIterable<File> getSubfolders(String path) {
    return getSubfolders(new File(path), new QList<File>());
  }

  public static QIterable<File> findInParentFolders(String path, Predicate<File> predicate) {
    QList<File> matches = new QList<>();
    File currentDirectory = new File(path);

    do {
      if (predicate.test(currentDirectory)) {
        matches.add(currentDirectory);
      }

      if (currentDirectory.getParent() != null) {
        currentDirectory = new File(currentDirectory.getParent());
      }
    } while (currentDirectory.getParent() != null);

    return matches;
  }


  public static QIterable<File> findInSubfolders(String path, Predicate<File> predicate) {
    QList<File> resultList = new QList<>();
    QIterable<File> folders = getSubfolders(path);

    for (File folder : folders) {
      for (File file : folder.listFiles()) {
        if (predicate.test(file)) {
          resultList.add(file);
        }
      }
    }

    return resultList;
  }
}
