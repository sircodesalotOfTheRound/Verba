package com.verba.test.resolution.locals;

import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.symbols.resolution.fields.VariableTypeResolutionMetadata;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;
import com.verba.test.resolution.TestResolver;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-22.
 */
public class SimpleLocalTypeResolution {
    @Test
    public void simpleLocalResolution() throws Exception {
        VerbaExpression page = VerbaCodePage.fromPackageItem(null, this.getClass(), "SimpleLocalResolution.v");

        StaticSpaceExpression staticSpaceExpression = new StaticSpaceExpression(page);
        GlobalSymbolTable symbolTable = staticSpaceExpression.globalSymbolTable();
        TestResolver resolver = new TestResolver(symbolTable);

        // Make sure the type for each item has been correctly cached.
        assert (resolver.getTypeForVariable("str").equals("String"));
        assert (resolver.getTypeForVariable("int").equals("Number"));
        assert (resolver.getTypeForVariable("string").equals("String"));
        assert (resolver.getTypeForVariable("integer").equals("Number"));
        assert (resolver.getTypeForVariable("inner").equals("MyClass.InnerClass"));

        assert (resolver.getTypeForVariable("stringLiteral").equals("VM.String"));
        assert (resolver.getTypeForVariable("int32Literal").equals("VM.Int32"));

        // Make sure the type resolution is cached for each item.
        assert (assertContainsCachedTypeResolution(symbolTable, "myFunction.str"));
        assert (assertContainsCachedTypeResolution(symbolTable, "myFunction.int"));
        assert (assertContainsCachedTypeResolution(symbolTable, "myFunction.string"));
        assert (assertContainsCachedTypeResolution(symbolTable, "myFunction.integer"));
        assert (assertContainsCachedTypeResolution(symbolTable, "myFunction.inner"));

        assert (assertContainsCachedTypeResolution(symbolTable, "myFunction.stringLiteral"));
        assert (assertContainsCachedTypeResolution(symbolTable, "myFunction.int32Literal"));
    }

    public boolean assertContainsCachedTypeResolution(GlobalSymbolTable symbolTable, String name) {
        SymbolTableEntry entry = symbolTable.getByFqn(name).single();
        return (entry.metadata().ofType(VariableTypeResolutionMetadata.class).any());
    }


}
