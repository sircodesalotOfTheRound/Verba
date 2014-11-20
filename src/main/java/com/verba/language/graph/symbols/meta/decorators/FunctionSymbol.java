package com.verba.language.graph.symbols.meta.decorators;

import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.graph.symbols.meta.NestedSymbolTableMetadata;
import com.verba.language.graph.symbols.resolution.function.FunctionReturnTypeResolutionMetadata;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-5-29.
 */
public class FunctionSymbol extends SymbolTableDecorator {

  private final FunctionDeclarationExpression function;
  private final SymbolTableEntry entry;
  private final ScopedSymbolTable scopedSymbolTable;
  private final TypeDeclarationExpression returnType;

  public FunctionSymbol(SymbolTableEntry entry) {
    super(entry);

    this.entry = entry;
    this.function = (FunctionDeclarationExpression) entry.instance();
    this.scopedSymbolTable = super.getSingleMetadataEntry(NestedSymbolTableMetadata.class).symbolTable();
    this.returnType = getReturnType(entry);
  }

  private TypeDeclarationExpression getReturnType(SymbolTableEntry entry) {
    FunctionReturnTypeResolutionMetadata metadata = entry.metadata().ofType(FunctionReturnTypeResolutionMetadata.class).single();
    return metadata.symbolType();
  }

  public ScopedSymbolTable scope() {
    return this.scopedSymbolTable;
  }

  public FunctionDeclarationExpression function() {
    return this.function;
  }

  public SymbolTableEntry entry() {
    return this.entry;
  }

  public TypeDeclarationExpression returnType() {
    return this.returnType;
  }

  public TupleDeclarationExpression parameters() {
    return this.function.parameterSets().single();
  }

  public GenericTypeListExpression genericParameters() {
    return this.function.genericParameters();
  }

  public boolean hasParameters() {
    return this.function.hasParameters();
  }

  public boolean hasGenericParameters() {
    return this.function.hasGenericParameters();
  }

  public BlockDeclarationExpression block() {
    return function.block();
  }

  public String name() {
    return function.name();
  }

  public boolean isClosure() {
    return false;
  }

  public String fqn() {
    return this.entry.fqn();
  }
}
