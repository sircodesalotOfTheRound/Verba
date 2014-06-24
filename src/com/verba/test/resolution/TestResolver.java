package com.verba.test.resolution;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.InvokableExpression;
import com.verba.language.expressions.categories.NamedDataDeclarationExpression;
import com.verba.language.symbols.meta.interfaces.SymbolTypeMetadata;
import com.verba.language.symbols.resolution.fields.VariableTypeResolver;
import com.verba.language.symbols.resolution.function.FunctionReturnTypeResolutionMetadata;
import com.verba.language.symbols.resolution.function.FunctionReturnTypeResolver;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;

/**
 * Created by sircodesalot on 14-5-27.
 */
// Object resolution helper class
public class TestResolver {
    private final GlobalSymbolTable symbolTable;
    private final VariableTypeResolver resolver;

    public TestResolver(GlobalSymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.resolver = new VariableTypeResolver(symbolTable);
    }

    public String getTypeForVariable(String name) {
        VerbaExpression variable = this.symbolTable.getByFriendlyName(name).single().instance();
        SymbolTypeMetadata resolutionMetadata = this.resolver.resolve((NamedDataDeclarationExpression) variable);

        return resolutionMetadata.symbolType().representation();
    }

    public String getTypeForFunction(String name) {
        SymbolTableEntry explicitlyTypedFunction = symbolTable.getByFqn(name).single();

        FunctionReturnTypeResolver resolver = new FunctionReturnTypeResolver(symbolTable);
        FunctionReturnTypeResolutionMetadata metadata;
        metadata = resolver.resolve(explicitlyTypedFunction.instanceAs(InvokableExpression.class));

        return metadata.symbolType().representation();
    }
}
