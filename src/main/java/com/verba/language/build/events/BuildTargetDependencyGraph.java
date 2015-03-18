package com.verba.language.build.events;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.tools.datastructures.OneToManyMap;
import com.verba.tools.polymorphism.ClassHierarchyFlattener;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class BuildTargetDependencyGraph {
  private final OneToManyMap<Class, BuildTarget> targetMap = new OneToManyMap<>();

  public void addTarget(BuildTarget target) {
    QIterable<Class> allDependencies = target.dependencies()
      .map(ClassHierarchyFlattener::new)
      .flatten(ClassHierarchyFlattener::flattenedHierarchy);

    for (Class dependency : allDependencies) {
      targetMap.add(dependency, target);
    }
  }

  public boolean containsTargetsForDependency(BuildArtifact artifact) {
    return targetMap.containsKey(artifact.getClass());
  }

  public QIterable<BuildTarget> getTargetsForDependency(BuildArtifact artifact) {
    return targetMap.getItemsForKey(artifact.getClass());
  }
}
