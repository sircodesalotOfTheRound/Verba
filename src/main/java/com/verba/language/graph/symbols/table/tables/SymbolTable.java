package com.verba.language.graph.symbols.table.tables;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.expressions.functions.NativeTypeSymbols;
import com.verba.language.graph.symbols.meta.NestedScopeMetadata;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14-5-16.
 */
public class SymbolTable {
  private static final QIterable<Symbol> EMPTY_SET = new QList<>();

  private final Scope rootTable;
  private final NativeTypeSymbols nativeTypeSymbols;
  private final QList<Symbol> entries = new QList<>();
  private final Map<VerbaExpression, Symbol> entriesByInstance = new HashMap<>();
  private final Map<String, QList<Symbol>> entriesByFriendlyName = new HashMap<>();
  private final Map<String, QList<Symbol>> entriesByFqn = new HashMap<>();

  public SymbolTable(SymbolTableExpression block) {
    this(new Scope(block));
  }

  public SymbolTable(Scope table) {
    this.rootTable = table;
    this.nativeTypeSymbols = this.addNativeTypes();
    this.scanTableHierarchy(table);
  }

  private NativeTypeSymbols addNativeTypes() {
    QIterable<Symbol> nativeTypeSymbolTableEntries = KeywordToken.vmTypeKeywords()
      .map(primitive -> new Symbol(primitive, rootTable, null));

    for (Symbol entry : nativeTypeSymbolTableEntries) {
      this.putEntry(entry);
    }

    return new NativeTypeSymbols(this);
  }

  private void scanTableHierarchy(Scope table) {
    for (Symbol entry : table.entries()) {
      this.putEntry(entry);
    }

    for (Scope subTable : table.nestedTables()) {
      scanTableHierarchy(subTable);
    }
  }

  private void putEntry(Symbol entry) {
    String friendlyName = entry.name();
    String fqn = entry.fqn();
    VerbaExpression instance = entry.expression();

    // Add to all entry lists
    this.entries.add(entry);
    this.entriesByInstance.put(instance, entry);
    this.getEntryListByFriendlyName(friendlyName).add(entry);
    this.getEntryListByFqn(fqn).add(entry);
  }

  private QList<Symbol> getEntryListByFriendlyName(String friendlyName) {
    // If there is already a list associated with this name,
    // then just return that.
    if (this.entriesByFriendlyName.containsKey(friendlyName)) {
      return this.entriesByFriendlyName.get(friendlyName);
    }

    // Else create a new list
    QList<Symbol> entryList = new QList<>();
    this.entriesByFriendlyName.put(friendlyName, entryList);

    return entryList;
  }

  private QList<Symbol> getEntryListByFqn(String fqn) {
    // If there is already a list associated with this name,
    // then just return that.
    if (this.entriesByFqn.containsKey(fqn)) {
      return this.entriesByFqn.get(fqn);
    }

    // Else create a new list
    QList<Symbol> entryList = new QList<>();
    this.entriesByFqn.put(fqn, entryList);

    return entryList;
  }

  public QIterable<Symbol> entries() {
    return this.entries;
  }

  public QIterable<Symbol> findAllMatchingFriendlyName(String friendlyName) {
    return this.entriesByFriendlyName.get(friendlyName);
  }

  public QIterable<Symbol> findAllMatchingFqn(String fqn) {
    QIterable<Symbol> matches =  this.entriesByFqn.get(fqn);

    if (matches != null) {
      return matches;
    } else {
      return EMPTY_SET;
    }
  }

  public Symbol findSymbolForType(String fqn) {
    if (this.nativeTypeSymbols.isNativeTypeSymbol(fqn)) {
      return this.nativeTypeSymbols.findNativeTypeSymbolByName(fqn);
    }

    return this.findAllMatchingFqn(fqn).single(entry -> entry.is(PolymorphicDeclarationExpression.class));
  }

  public Scope resolveScope(VerbaExpression expression) {
    if (expression == null) {
      return null;
    } else if (this.entriesByInstance.containsKey(expression)) {
      Symbol symbol = this.entriesByInstance.get(expression);

      return symbol
        .metadata()
        .ofType(NestedScopeMetadata.class)
        .single()
        .nestedScope();
    }

    return resolveScope(expression.parent());
  }

  public Symbol findByIndex(int index) {
    return this.entries.get(index);
  }

  public Symbol findByInstance(VerbaExpression instance) {
    return this.entriesByInstance.get(instance);
  }

  public Scope rootTable() {
    return this.rootTable;
  }
}
