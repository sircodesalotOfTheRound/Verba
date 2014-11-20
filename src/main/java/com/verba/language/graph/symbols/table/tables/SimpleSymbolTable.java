package com.verba.language.graph.symbols.table.tables;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntrySet;

/**
 * A symbol table for nonspecific use. Has most basic symbol table functionality, but primarily
 * used as just a simple way of storing named entries.
 */
public class SimpleSymbolTable {
  // The entry set table containing the entries. We associate it with 'null' table
  // because we always want the entries to refer back to their actual parent table.
  private final SymbolTableEntrySet table = new SymbolTableEntrySet(null);

  public void add(SymbolTableEntry entry) {
    this.table.add(entry);
  }

  public QIterable<SymbolTableEntry> entries() {
    return this.table.entries();
  }

  public QIterable<SymbolTableEntry> entryByName(String name) {
    return this.entryByName(name);
  }

  public boolean containsKey(String name) {
    return this.table.containsKey(name);
  }
}
