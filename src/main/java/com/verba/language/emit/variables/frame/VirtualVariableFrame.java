package com.verba.language.emit.variables.frame;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.tools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 14/12/4.
 */
public class VirtualVariableFrame {
  private final QList<VirtualVariable> variablesInFrame = new QList<>();
  private VirtualVariable returnValue;

  public void add(VirtualVariable variable) {
    this.variablesInFrame.add(variable);
  }

  public QIterable<VirtualVariable> variables() { return this.variablesInFrame; }

  public boolean hasReturnValue() { return this.returnValue != null; }

  public VirtualVariable returnValue() { return this.returnValue; }

  public void setReturnValue(VirtualVariable returnValue) {
    if (hasReturnValue()) {
      throw new CompilerException("Return value has already been set");
    }

    this.returnValue = returnValue;
  }
}
