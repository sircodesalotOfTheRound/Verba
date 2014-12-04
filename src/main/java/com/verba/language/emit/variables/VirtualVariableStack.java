package com.verba.language.emit.variables;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.tools.exceptions.CompilerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class VirtualVariableStack {
  private final VirtualVariable[] variableArray;
  private final Map<String, VirtualVariable> variablesByName = new HashMap<>();
  private final QSet<VirtualVariable> variablesBySet = new QSet<>();
  private final QSet<Integer> availableRegisters = new QSet<>();
  private final Stack<VirtualVariableFrame> callFrames = new Stack<>();

  public VirtualVariableStack(int size) {
    this.variableArray = new VirtualVariable[size];

    for (int index = 0; index < size; index++) {
      this.availableRegisters.add(index);
    }

    this.callFrames.push(new VirtualVariableFrame());
  }

  public VirtualVariable add(String key, SymbolTableEntry type) {
    if (this.containsKey(key)) {
      throw new CompilerException("Key already exists");
    }

    return add(key, type, nextAvailableIndex());
  }

  public VirtualVariable add(String key, SymbolTableEntry type, int variableNumber) {
    VirtualVariable variable = new VirtualVariable(key, variableNumber, type);
    add(variable);

    return variable;
  }

  public VirtualVariable variableByName(String key) {
    return this.variablesByName.get(key);
  }

  public boolean containsKey(String key) {
    return this.variablesByName.containsKey(key);
  }

  public void add(VirtualVariable variable) {
    this.variableArray[variable.variableNumber()] = variable;

    this.variablesByName.put(variable.key(), variable);
    this.variablesBySet.add(variable);
    this.availableRegisters.remove(variable.variableNumber());
  }

  public void pushFrame() {
    this.callFrames.push(new VirtualVariableFrame());
  }

  public VirtualVariableFrame currentFrame() { return this.callFrames.peek(); }

  public void popFrame() {
    VirtualVariableFrame frame = this.callFrames.pop();

    for (VirtualVariable variable : frame.variables()) {
      this.expireVariable(variable);
    }
  }

  private void expireVariable(int variableNumber) {
    this.availableRegisters.add(variableNumber);
  }

  public void expireVariable(VirtualVariable variable) {
    expireVariable(variable.variableNumber());
  }

  public int size() { return this.variableArray.length; }

  private int nextAvailableIndex() {
    return this.availableRegisters.first();
  }
}
