package com.verba.language.graph.symbols.resolution.function;

import com.verba.language.graph.tools.SyntaxTreeFlattener;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.categories.*;
import com.verba.language.parsing.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.graph.symbols.meta.NestedSymbolTableMetadata;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTypeMetadata;
import com.verba.language.graph.symbols.resolution.fields.VariableNameSearch;
import com.verba.language.graph.symbols.resolution.fields.VariableTypeResolutionMetadata;
import com.verba.language.graph.symbols.resolution.fields.VariableTypeResolver;
import com.verba.language.graph.symbols.resolution.interfaces.SymbolResolutionInfo;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.virtualmachine.VirtualMachineNativeTypes;

/**
 * Created by sircodesalot on 14-5-25.
 */
public class FunctionReturnTypeResolutionMetadata implements SymbolResolutionInfo, SymbolTypeMetadata, SymbolTableMetadata {
  private final GlobalSymbolTable symbolTable;
  private final ScopedSymbolTable scope;

  private TypeDeclarationExpression type;

  public FunctionReturnTypeResolutionMetadata(GlobalSymbolTable symbolTable, InvokableExpression expression) {
    this.symbolTable = symbolTable;
    this.scope = getScope(symbolTable, expression);

    if (expression.hasTypeConstraint())
      this.type = expression.typeDeclaration();
    else
      this.type = this.scanFunction(expression.block());
  }

  private ScopedSymbolTable getScope(GlobalSymbolTable symbolTable, InvokableExpression expression) {
    SymbolTableEntry entry = symbolTable.getByInstance((VerbaExpression) expression);
    NestedSymbolTableMetadata metadata = entry.metadata().ofType(NestedSymbolTableMetadata.class).single();

    return metadata.symbolTable();
  }

  private TypeDeclarationExpression getTypeFromExpression(ReturnStatementExpression statement) {
    RValueExpression value = statement.value();

    if (value instanceof NativeTypeExpression) {
      return ((NativeTypeExpression) value).nativeTypeDeclaration();
    }

    if (value instanceof NamedValueExpression) {
      String variableName = ((NamedValueExpression) value).name();
      VariableNameSearch search = new VariableNameSearch(symbolTable, scope, variableName);

      return search.resolvedType();
    }

    // Todo: make this more robust
    return VirtualMachineNativeTypes.UNIT_EXPRESSION;
  }

  private TypeDeclarationExpression scanFunction(BlockDeclarationExpression block) {
    SyntaxTreeFlattener scopeTree = new SyntaxTreeFlattener(block);
    for (ReturnStatementExpression statement : scopeTree.ofType(ReturnStatementExpression.class)) {
      if (statement.hasValue()) {
        return getTypeFromExpression(statement);
      }
    }

    return VirtualMachineNativeTypes.UNIT_EXPRESSION;
  }

  // TODO: Named resolution needs to be abstracted out into a class.
  public TypeDeclarationExpression resolveFromVariableName(VerbaExpression value) {
    NamedValueExpression variable = (NamedValueExpression) value;
    String localName = variable.name();

    if (this.scope.containsKey(localName)) {
      SymbolTableEntry local = this.scope.get(localName).single();
      VariableTypeResolver variableTypeResolver = new VariableTypeResolver(symbolTable);
      VariableTypeResolutionMetadata resolution = variableTypeResolver.resolve((NamedAndTypedExpression) local.instance());

      return resolution.symbolType();
    }

    return null;
  }

  @Override
  public TypeDeclarationExpression symbolType() {
    return this.type;
  }
}
