package com.verba.language.graph.symbols.table.tables;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.expressions.functions.NativeTypeSymbols;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14-5-16.
 */
public class GlobalSymbolTable {
  private static final QIterable<SymbolTableEntry> EMPTY_SET = new QList<>();

  private final ScopedSymbolTable rootTable;
  private final NativeTypeSymbols nativeTypeSymbols;
  private final QList<SymbolTableEntry> entries = new QList<>();
  private final Map<VerbaExpression, SymbolTableEntry> entriesByInstance = new HashMap<>();
  private final Map<String, QList<SymbolTableEntry>> entriesByFriendlyName = new HashMap<>();
  private final Map<String, QList<SymbolTableEntry>> entriesByFqn = new HashMap<>();

  public GlobalSymbolTable(SymbolTableExpression block) {
    this(new ScopedSymbolTable(block));
  }

  public GlobalSymbolTable(ScopedSymbolTable table) {
    this.rootTable = table;
    this.nativeTypeSymbols = this.addNativeTypes();
    this.scanTableHierarchy(table);
  }

  private NativeTypeSymbols addNativeTypes() {
    QIterable<SymbolTableEntry> nativeTypeSymbolTableEntries = KeywordToken.nativeTypeKeywords()
      .map(primitive -> new SymbolTableEntry(primitive, rootTable, null));

    for (SymbolTableEntry entry : nativeTypeSymbolTableEntries) {
      this.putEntry(entry);
    }

    return new NativeTypeSymbols(this);
  }

  private void scanTableHierarchy(ScopedSymbolTable table) {
    for (SymbolTableEntry entry : table.entries()) {
      this.putEntry(entry);
    }

    for (ScopedSymbolTable subTable : table.nestedTables()) {
      scanTableHierarchy(subTable);
    }
  }

  private void putEntry(SymbolTableEntry entry) {
    String friendlyName = entry.name();
    String fqn = entry.fqn();
    VerbaExpression instance = entry.instance();

    // Add to all entry lists
    this.entries.add(entry);
    this.entriesByInstance.put(instance, entry);
    this.getEntryListByFriendlyName(friendlyName).add(entry);
    this.getEntryListByFqn(fqn).add(entry);
  }

  public QList<SymbolTableEntry> getEntryListByFriendlyName(String friendlyName) {
    // If there is already a list associated with this name,
    // then just return that.
    if (this.entriesByFriendlyName.containsKey(friendlyName)) {
      return this.entriesByFriendlyName.get(friendlyName);
    }

    // Else create a new list
    QList<SymbolTableEntry> entryList = new QList<>();
    this.entriesByFriendlyName.put(friendlyName, entryList);

    return entryList;
  }

  public QList<SymbolTableEntry> getEntryListByFqn(String fqn) {
    // If there is already a list associated with this name,
    // then just return that.
    if (this.entriesByFqn.containsKey(fqn)) {
      return this.entriesByFqn.get(fqn);
    }

    // Else create a new list
    QList<SymbolTableEntry> entryList = new QList<>();
    this.entriesByFqn.put(fqn, entryList);

    return entryList;
  }

  public NativeTypeSymbols nativeTypeSymbols() { return this.nativeTypeSymbols; }
  public QIterable<SymbolTableEntry> entries() {
    return this.entries;
  }

  public QIterable<SymbolTableEntry> getByFriendlyName(String friendlyName) {
    return this.entriesByFriendlyName.get(friendlyName);
  }

  public QIterable<SymbolTableEntry> getByFqn(String fqn) {
    QIterable<SymbolTableEntry> matches =  this.entriesByFqn.get(fqn);

    if (matches != null) {
      return matches;
    } else {
      return EMPTY_SET;
    }
  }

  public SymbolTableEntry getEntryForSymbolType(String fqn) {
    return this.getByFqn(fqn).single(entry -> entry.is(PolymorphicDeclarationExpression.class));
  }

  public SymbolTableEntry getByIndex(int index) {
    return this.entries.get(index);
  }

  public SymbolTableEntry getByInstance(VerbaExpression instance) {
    return this.entriesByInstance.get(instance);
  }

  public ScopedSymbolTable rootTable() {
    return this.rootTable;
  }
}
