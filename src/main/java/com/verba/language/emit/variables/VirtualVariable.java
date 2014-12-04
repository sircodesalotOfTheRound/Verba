package com.verba.language.emit.variables;

import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class VirtualVariable {
  private final int variableNumber;
  private final SymbolTableEntry type;
  private final String key;

  public VirtualVariable(String key, int variableNumber, SymbolTableEntry type) {
    this.key = key;
    this.variableNumber = variableNumber;
    this.type = type;
  }

  public int variableNumber() { return this.variableNumber; }
  public SymbolTableEntry type() { return this.type; }
  public String key() { return key; }
}
