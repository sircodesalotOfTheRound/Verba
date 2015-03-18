package com.verba.tools.polymorphism;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class ClassHierarchyFlattener {
  private final Class type;
  private final QIterable<Class> flattenedHierarchy;

  public ClassHierarchyFlattener(Class type) {
    this.type = type;
    this.flattenedHierarchy = collectInterfacesForType(type);
  }

  private QIterable<Class> collectInterfacesForType(Class type) {
    return collectInterfacesForType(type, new QList<>());
  }

  private QIterable<Class> collectInterfacesForType(Class type, QList<Class> interfaces) {
    if (type == null) return interfaces;

    collectInterfacesForType(type.getSuperclass(), interfaces);
    for (Class iface : type.getInterfaces()) {
      collectInterfacesForType(iface, interfaces);
    }

    interfaces.add(type);
    return interfaces;
  }

  public Class type() { return this.type; }
  public QIterable<Class> flattenedHierarchy() { return this.flattenedHierarchy; }
}
