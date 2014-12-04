package com.verba.language.emit.variables;

import com.javalinq.implementations.QSet;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;
import sun.jvm.hotspot.memory.SymbolTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class VirtualVariableSet {
  private final VirtualVariable[] variablesByList;
  private final Map<String, VirtualVariable> variablesByExpression = new HashMap<>();
  private final QSet<VirtualVariable> variablesBySet = new QSet<>();
  private final QSet<Integer> availableRegisters = new QSet<>();

  public VirtualVariableSet(int size) {
    this.variablesByList = new VirtualVariable[size];

    for (int index = 0; index < size; index++) {
      this.availableRegisters.add(index);
    }
  }

  public VirtualVariable add(String key, SymbolTableEntry type) {
    return add(key, type, nextAvailableIndex());
  }

  public VirtualVariable add(String key, SymbolTableEntry type, int variableNumber) {
    VirtualVariable variable = new VirtualVariable(key, variableNumber, type);
    add(variable);

    return variable;
  }

  public VirtualVariable variableByExpression(VerbaExpression expression) {
    return this.variablesByExpression.get(expression.text());
  }

  public void add(VirtualVariable variable) {
    this.variablesByList[variable.variableNumber()] = variable;

    this.variablesByExpression.put(variable.key(), variable);
    this.variablesBySet.add(variable);
    this.availableRegisters.remove(variable.variableNumber());
  }

  public void expireVariable(int variableNumber) {
    this.availableRegisters.add(variableNumber);
  }

  public void expireVariable(VirtualVariable variable) {
    expireVariable(variable.variableNumber());
  }

  public int nextAvailableIndex() {
    return this.availableRegisters.first();
  }
}
