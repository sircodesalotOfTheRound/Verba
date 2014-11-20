package com.verba.language.graph.symbols.resolution.fields;

import com.verba.language.graph.statictyping.SymbolTypeResolver;
import com.verba.language.parsing.expressions.categories.ResolvableTypeExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.expressions.categories.TypedExpression;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14/9/13.
 */
public class VariableNameSearch {
  private final GlobalSymbolTable symbolTable;
  private final ScopedSymbolTable startingScope;
  private final SymbolTableEntry resolvedEntry;
  private boolean isClosedOver;

  public VariableNameSearch(GlobalSymbolTable symbolTable, ScopedSymbolTable startingScope, String name) {
    this.symbolTable = symbolTable;
    this.startingScope = startingScope;
    this.resolvedEntry = findEntryRecursive(startingScope, name);
    this.isClosedOver = determineIsClosedOver();
  }

  private SymbolTableEntry findEntryRecursive(ScopedSymbolTable startingTable, String name) {
    if (startingTable.containsKey(name)) {
      return startingTable.get(name).single();
    } else {
      if (startingTable.hasParentTable()) {
        return findEntryRecursive(startingTable.parent(), name);
      } else {
        return null;
      }
    }
  }

  private boolean determineIsClosedOver() {
    if (resolvedEntry == null) {
      return false;
    }

    return (startingScope != resolvedEntry.table());
  }

  private void resolveEntry(SymbolTableEntry entry) {
    SymbolTypeResolver resolver = new SymbolTypeResolver(symbolTable);
    resolver.resolve((ResolvableTypeExpression) entry.instance());
  }

  private TypeDeclarationExpression getTypeFromSymbolEntry(SymbolTableEntry entry) {
    TypedExpression instance = (TypedExpression)entry.instance();

    if (instance.hasTypeConstraint()) {
      return instance.typeDeclaration();
    } else {
      if (!entry.metadata().ofType(VariableTypeResolutionMetadata.class).any()) {
        resolveEntry(entry);
      }

      return entry.metadata().ofType(VariableTypeResolutionMetadata.class).single().symbolType();
    }
  }

  public boolean isClosedOver() { return isClosedOver; }
  public boolean nameResolved() { return resolvedEntry != null; }
  public SymbolTableEntry resolvedEntry() { return resolvedEntry; }
  public TypeDeclarationExpression resolvedType() { return getTypeFromSymbolEntry(resolvedEntry); }

}



