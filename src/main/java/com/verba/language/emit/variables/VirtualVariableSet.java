package com.verba.language.emit.variables;

import com.javalinq.implementations.QMap;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.tools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 15/3/24.
 */
public class VirtualVariableSet {
  private final QMap<String, VirtualVariable> variablesByName = new QMap<>();
  private final QMap<Integer, VirtualVariable> variablesByIndex = new QMap<>();
  private int variableCount = 0;

  public boolean containsKey(String key) { return variablesByName.containsKey(key); }
  public boolean containsIndex(Integer index) { return variablesByIndex.containsKey(index); }

  public void add(String key, Symbol type) {
    VirtualVariable variable = new VirtualVariable(key, variableCount++, type);
    add(key, variable);
  }

  private void add(String key, VirtualVariable variable) {
    if (containsKey(key)) {
      throw new CompilerException("Variable by this name already exists");
    } else {
      variablesByName.add(key, variable);
      variablesByIndex.add(variableCount++, variable);
    }
  }

  public VirtualVariable get(int index) {
    return variablesByIndex.get((Integer) index);
  }

  public VirtualVariable get(String key) {
    return variablesByName.get(key);
  }
}
