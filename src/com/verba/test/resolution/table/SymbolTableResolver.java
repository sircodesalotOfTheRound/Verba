package com.verba.test.resolution.table;

import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.build.resolution.SymbolResolver;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.InvokableExpression;
import com.verba.language.expressions.categories.NamedDataDeclarationExpression;
import com.verba.language.symbols.resolution.fields.VariableTypeResolutionMetadata;
import com.verba.language.symbols.resolution.function.FunctionReturnTypeResolutionMetadata;
import com.verba.language.symbols.resolution.polymorphic.PolymorphicResolutionMetadata;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-28.
 */
public class SymbolTableResolver {

    @Test
    public void resolveSymbolTable() {
        VerbaCodePage page = VerbaCodePage.fromPackageItem(null, this.getClass(), "SymbolTableResolution.v");
        StaticSpaceExpression staticSpaceExpression = new StaticSpaceExpression(page);

        // Resolve all symbols
        SymbolResolver resolver = new SymbolResolver(staticSpaceExpression.globalSymbolTable());
        GlobalSymbolTable symbolTable = staticSpaceExpression.globalSymbolTable();

        assertContainsCachedTypeResolution(symbolTable, "Fighter.fight", "VM.Unit");
        assertContainsCachedTypeResolution(symbolTable, "Person.age", "VM.Int32");


        assertContainsMembers(symbolTable, "Fighter", "fight");
        assertContainsMembers(symbolTable, "Lover", "love");
        assertContainsMembers(symbolTable, "Person", "age");

        assertContainsMembers(symbolTable, "Roles", "age", "love", "fight");

    }

    public void assertContainsCachedTypeResolution(GlobalSymbolTable symbolTable, String name, String type) {
        SymbolTableEntry entry = symbolTable.getByFqn(name).single();
        VerbaExpression expression = entry.instance();

        if (expression instanceof InvokableExpression)
            assert (entry.metadata().ofType(FunctionReturnTypeResolutionMetadata.class)
                .single()
                .symbolType()
                .representation()
                .equals(type));

//        else if (expression instanceof PolymorphicExpression)
//            entry.metadata().ofType(PolymorphicResolutionMetadata.class)
//                .single()
//                .()
//                .representation()
//                .equals(type);

        else if (expression instanceof NamedDataDeclarationExpression)
            assert (entry.metadata().ofType(VariableTypeResolutionMetadata.class)
                .single()
                .symbolType()
                .representation()
                .equals(type));
    }

    public void assertContainsMembers(GlobalSymbolTable symbolTable, String className, String... names) {
        SymbolTableEntry entry = symbolTable.getByFqn(className).single();
        PolymorphicResolutionMetadata metadata = entry.metadata().ofType(PolymorphicResolutionMetadata.class).single();

        for (String name : names) {
            assert (metadata.containsKey(name));
        }
    }

}
