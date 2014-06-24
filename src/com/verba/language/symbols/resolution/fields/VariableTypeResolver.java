package com.verba.language.symbols.resolution.fields;

import com.verba.language.exceptions.CompilerException;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.*;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.symbols.resolution.interfaces.SymbolResolver;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.symbols.table.tables.ScopedSymbolTable;
import com.verba.virtualmachine.VirtualMachineNativeTypes;

/**
 * Created by sircodesalot on 14-5-22.
 */
public class VariableTypeResolver implements SymbolResolver<NamedDataDeclarationExpression, VariableTypeResolutionMetadata> {
    private final GlobalSymbolTable symbolTable;

    public VariableTypeResolver(GlobalSymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public VariableTypeResolutionMetadata resolve(NamedDataDeclarationExpression expression) {
        SymbolTableEntry entry = symbolTable.getByInstance((VerbaExpression) expression);

        // If this item has an explicit type constrait, return that
        // TODO: Make this verify that the type constraint is in fact correct.
        if (expression.hasTypeConstraint()) {
            VariableTypeResolutionMetadata metadata = new VariableTypeResolutionMetadata(entry, expression.typeDeclaration());
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
        LiteralExpression literalExpression = (LiteralExpression) assignment.rvalue();

        if (literalExpression instanceof QuoteExpression)
            return new VariableTypeResolutionMetadata(null, VirtualMachineNativeTypes.STRING_LITERAL);

        else if (literalExpression instanceof NumericExpression)
            return new VariableTypeResolutionMetadata(null, VirtualMachineNativeTypes.INT32_LITERAL);

        // This should never happen. If it does, means that we don't have a specific type
        // declared for this type of literal.
        throw new CompilerException("Invalid literal type.");
    }

    // TODO: Make this more powerful. Currently only resolves items in the current scope.
    private VariableTypeResolutionMetadata resolveNamedExpression(SymbolTableEntry entry, AssignmentExpression assignment) {
        NamedExpression namedRValue = (NamedExpression) assignment.rvalue();
        ScopedSymbolTable scope = symbolTable.getByInstance((VerbaExpression) assignment).table();

        SymbolTableEntry resolvedEntry = scope.get(namedRValue.name()).single();

        VerbaExpression instance = scope.get(namedRValue.name()).single().instance();
        TypedExpression instanceAsTypedExpression = (TypedExpression) instance;

        return new VariableTypeResolutionMetadata(entry, instanceAsTypedExpression.typeDeclaration());
    }
}
