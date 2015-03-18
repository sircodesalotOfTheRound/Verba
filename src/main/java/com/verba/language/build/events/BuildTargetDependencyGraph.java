package com.verba.language.build.events;

import com.javalinq.implementations.QList;
import com.javalinq.implementations.QMap;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.testtools.polymorphism.ClassHierarchyFlattener;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class BuildTargetDependencyGraph {
  private final QMap<Class, QList<BuildTarget>> targetMap = new QMap<>();

  public void addTarget(BuildTarget target) {
    QIterable<Class> allDependencies = target.dependencies()
      .map(ClassHierarchyFlattener::new)
      .flatten(ClassHierarchyFlattener::flattenedHierarchy);

  }
}
