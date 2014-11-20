package com.verba.language.symbols.resolution.fields;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.*;
import com.verba.language.symbols.resolution.interfaces.SymbolResolver;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-5-22.
 */
public class VariableTypeResolver implements SymbolResolver<NamedAndTypedExpression, VariableTypeResolutionMetadata> {
  private final GlobalSymbolTable symbolTable;

  public VariableTypeResolver(GlobalSymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  @Override
  public VariableTypeResolutionMetadata resolve(NamedAndTypedExpression expression) {
    SymbolTableEntry entry = symbolTable.getByInstance((VerbaExpression) expression);

    if (entry.metadata().ofType(VariableTypeResolutionMetadata.class).any()) {
      return entry.metadata().ofType(VariableTypeResolutionMetadata.class).single();
    }

    // If this item has an explicit type constraint, return that
    // TODO: Make this verify that the type constraint is in fact correct.
    // TODO: Also verify whether or not the value is closed over.
    if (expression.hasTypeConstraint()) {
      VariableTypeResolutionMetadata metadata = new VariableTypeResolutionMetadata(entry, false, expression.typeDeclaration());
      entry.addMetadata(metadata);

      return metadata;
    }

    // If the declaration is also an assignment expression (val/muta)
    if (expression instanceof AssignmentExpression) {
      AssignmentExpression assignment = (AssignmentExpression) expression;

      // If the assignment has an R-Value (thing being assigned),
      // note that for muta expressions this is not required
      // if the type is explicitly declared (although this should be verified
      // before hand.
      if (assignment.hasRValue()) {
        VariableTypeResolutionMetadata metadata = null;

        if (assignment.rvalue() instanceof NamedExpression)
          metadata = resolveNamedExpression(entry, assignment);

        else if (assignment.rvalue() instanceof LiteralExpression)
          metadata = this.resolveLiteralExpression(assignment);

        // Cache the value for future reference
        entry.addMetadata(metadata);
        return metadata;
      }
    }

    return null;
  }

  private VariableTypeResolutionMetadata resolveLiteralExpression(AssignmentExpression assignment) {
    return new VariableTypeResolutionMetadata(null, false, ((NativeTypeExpression)assignment.rvalue()).nativeTypeDeclaration());
  }

  private VariableTypeResolutionMetadata resolveNamedExpression(SymbolTableEntry entry, AssignmentExpression assignment) {
    NamedExpression namedRValue = (NamedExpression) assignment.rvalue();
    ScopedSymbolTable scope = symbolTable.getByInstance((VerbaExpression) assignment).table();
    VariableNameSearch search = new VariableNameSearch(symbolTable, scope, namedRValue.name());

    return new VariableTypeResolutionMetadata(entry, search.isClosedOver(), search.resolvedType());
  }
}
