package com.verba.test.resolution.function;

import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.build.resolution.SymbolResolver;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.symbols.resolution.function.FunctionReturnTypeResolutionMetadata;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;
import com.verba.test.resolution.TestResolver;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-25.
 */
public class FunctionReturnTypeResolutionTesting {
    @Test
    public void checkExplicitylyTypedFunction() {
        VerbaCodePage page = VerbaCodePage.fromPackageItem(null, this.getClass(), "FunctionReturnTypeResolution.v");
        StaticSpaceExpression staticSpaceExpression = new StaticSpaceExpression(page);

        GlobalSymbolTable symbolTable = staticSpaceExpression.globalSymbolTable();
        SymbolResolver symbolResolver = new SymbolResolver(symbolTable);

        TestResolver resolver = new TestResolver(symbolTable);

        assert (resolver.getTypeForFunction("explicitString").equals("String"));
        assert (resolver.getTypeForFunction("implicitUnit").equals("VM.Unit"));
        assert (resolver.getTypeForFunction("implicitInt32").equals("VM.Int32"));
        assert (resolver.getTypeForFunction("implicitString").equals("VM.String"));
        assert (resolver.getTypeForFunction("returnedLocalString").equals("VM.String"));
        assert (resolver.getTypeForFunction("returnedLocalInteger").equals("Int32"));
        assert (resolver.getTypeForFunction("returnChainResolvedValue").equals("Int32"));


        assertContainsCachedTypeResolution(symbolTable, "explicitString");
        assertContainsCachedTypeResolution(symbolTable, "implicitUnit");
        assertContainsCachedTypeResolution(symbolTable, "implicitInt32");
        assertContainsCachedTypeResolution(symbolTable, "implicitString");
        assertContainsCachedTypeResolution(symbolTable, "returnedLocalString");
        assertContainsCachedTypeResolution(symbolTable, "returnChainResolvedValue");
    }

    public void assertContainsCachedTypeResolution(GlobalSymbolTable symbolTable, String name) {
        SymbolTableEntry entry = symbolTable.getByFqn(name).single();
        assert (entry.metadata().ofType(FunctionReturnTypeResolutionMetadata.class).any());
    }

}
