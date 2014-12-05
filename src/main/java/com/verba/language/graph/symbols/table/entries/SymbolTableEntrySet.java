package com.verba.language.graph.symbols.table.entries;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.parse.expressions.VerbaExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sircodesalot on 14-5-8.
 */
public class SymbolTableEntrySet {
  private final Scope table;
  private final Map<String, QList<Symbol>> entriesByName = new HashMap<>();
  private final QList<Symbol> entries = new QList<>();

  public SymbolTableEntrySet(Scope table) {
    this.table = table;
  }

  public void add(Symbol entry) {
    String name = entry.name();
    VerbaExpression instance = entry.instance();

    // Add Entry List
    this.getEntryListByName(name).add(entry);
    this.entries.add(entry);
  }

  public QList<Symbol> getEntryListByName(String forName) {
    // If there is already a list associated with this name,
    // then just return that.
    if (this.entriesByName.containsKey(forName)) {
      return this.entriesByName.get(forName);
    }

    // Else create a new list
    QList<Symbol> entryList = new QList<>();
    this.entriesByName.put(forName, entryList);

    return entryList;
  }

  public void add(String name, VerbaExpression expression, SymbolTableMetadata... metadata) {
    Symbol entry = new Symbol(name, table, expression, metadata);
    this.add(entry);
  }

  public QIterable<Symbol> entries() {
    return this.entries;
  }

  public boolean hasItems() {
    return this.entries.count() > 0;
  }

  public void clear() {
    this.entriesByName.clear();
    this.entries.clear();
  }

  public int getIndex(Symbol entry) {
    return this.entries.indexOf(entry);
  }

  public QIterable<Symbol> get(String key) {
    return this.entriesByName.get(key);
  }

  public QIterable<String> keys() {
    return new QList<>(this.entriesByName.keySet());
  }

  public boolean containsKey(String key) {
    return this.entriesByName.containsKey(key);
  }

  public long count() {
    return this.entries.count();
  }

  public Set<String> keySet() {
    return this.entriesByName.keySet();
  }

//    public void resolveMetadata() {
//        for (SymbolTableEntry entry : this.entries) {
//            for (SymbolTableResolver metadata : entry.metadata().ofType(SymbolTableResolver.class)) {
//                metadata.resolveBinding(table, entry);
//            }
//        }
//    }

  public Symbol get(int index) {
    return this.entries.get(index);
  }
}
