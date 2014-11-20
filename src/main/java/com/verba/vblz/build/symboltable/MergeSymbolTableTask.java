package com.verba.vblz.build.symboltable;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.tools.EnvironmentHelpers;
import com.verba.tools.files.FileTools;
import com.verba.tools.tasks.Task;

/**
 * Created by sircodesalot on 14/8/31.
 */
public class MergeSymbolTableTask implements Task {
  @Override
  public void perform() {
    QIterable<StaticSpaceExpression> staticSpaceExpressions = FileTools
      .findInSubfolders(EnvironmentHelpers.getSymbolFolderPath(), file -> file.getAbsolutePath().endsWith(".sym"))
      .map(file -> FileTools.deserializeObjectFromFile(file.getAbsolutePath()))
      .cast(StaticSpaceExpression.class);

    QList<Object> objects = FileTools.findInSubfolders(EnvironmentHelpers.getSymbolFolderPath(), file -> file.getAbsolutePath().endsWith(".sym"))
      .map(file -> FileTools.deserializeObjectFromFile(file.getAbsolutePath()))
      .toList();
  }
}
