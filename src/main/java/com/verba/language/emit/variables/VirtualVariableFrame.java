package com.verba.language.emit.variables;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 14/12/4.
 */
public class VirtualVariableFrame {
  QList<VirtualVariable> variablesInFrame = new QList<>();

  public void add(VirtualVariable variable) {
    this.variablesInFrame.add(variable);
  }

  public QIterable<VirtualVariable> variable() { return this.variablesInFrame; }
}
