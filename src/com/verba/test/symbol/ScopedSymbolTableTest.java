package com.verba.test.symbol;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.expressions.blockheader.namespaces.NamespaceDeclarationExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.statements.declaration.MutaDeclarationStatement;
import com.verba.language.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.expressions.tags.hashtag.HashTagExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.symbols.table.tables.ScopedSymbolTable;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-7.
 */
public class ScopedSymbolTableTest {
    @Test
    public void simpleNestedBlockScan() throws Exception {
        Lexer lexer = GeneralLexing.generateFromFileInThisPackage("SymbolTableTest.v");
        NamespaceDeclarationExpression namespace = (NamespaceDeclarationExpression) VerbaExpression.read(null, lexer);

        ScopedSymbolTable namespaceTable = new ScopedSymbolTable(namespace);
        assert (namespaceTable.get("aFunction") != null);

        ScopedSymbolTable aFunctionTable = namespaceTable.nestedTables().single();
        FunctionDeclarationExpression aFunction
            = (FunctionDeclarationExpression) namespaceTable.get("aFunction").single().instance();

        assert (aFunctionTable.entries().count() == 11);

        // Parameters
        assert (aFunctionTable.name().equals("aFunction"));
        assert (aFunctionTable.get("first").single().instance() instanceof VarNameDeclarationExpression);
        assert (aFunctionTable.get("second").single().instance() instanceof VarNameDeclarationExpression);

        // Generic Parameters
        assert (aFunctionTable.get("T").single().instance() instanceof VarNameDeclarationExpression);
        assert (aFunctionTable.get("U").single().instance() instanceof VarNameDeclarationExpression);
        assert (aFunctionTable.get("U").single().instanceAs(VarNameDeclarationExpression.class).hasTypeConstraint());
        assert (aFunctionTable.get("V").single().instance() instanceof VarNameDeclarationExpression);

        // In function declarations
        assert (aFunctionTable.get("item").single().instance() instanceof ValDeclarationStatement);
        assert (aFunctionTable.get("item").single().instanceAs(ValDeclarationStatement.class).hasTypeConstraint());
        assert (aFunctionTable.get("number").single().instance() instanceof MutaDeclarationStatement);
        assert (aFunctionTable.get("generic").single().instance() instanceof MutaDeclarationStatement);
        assert (aFunctionTable.get("generic").single().instanceAs(MutaDeclarationStatement.class).hasTypeConstraint());
        assert (aFunctionTable.get("third").single().instance() instanceof ValDeclarationStatement);

        assert (aFunctionTable.get("internalFunction").single().instance() instanceof FunctionDeclarationExpression);
        assert (aFunctionTable.get("MyClass").single().instance() instanceof ClassDeclarationExpression);

        assert (aFunction.block().single(entry -> entry instanceof HashTagExpression) != null);

        assert (aFunctionTable.nestedTables().count() == 2);
        ScopedSymbolTable internalFunctionTable = aFunctionTable.nestedTables()
            .where(item -> item.hasName())
            .single(item -> item.name().equals("internalFunction"));

        assert (internalFunctionTable.name().equals("internalFunction"));

        assert (internalFunctionTable.entries().count() == 3);

        assert (internalFunctionTable.get("param").single().instance() instanceof VarNameDeclarationExpression);
        assert (internalFunctionTable.get("another").single().instance() instanceof ValDeclarationStatement);
        assert (internalFunctionTable.get("number").single().instance() instanceof MutaDeclarationStatement);

        assert (internalFunctionTable.containsKey("number"));
        assert (internalFunctionTable.parent().containsKey("number"));

        ScopedSymbolTable myClassTable = aFunctionTable.nestedTables()
            .where(item -> item.hasName())
            .single(item -> item.name().equals("MyClass"));

        assert (myClassTable.get("number").single().instance() instanceof MutaDeclarationStatement);
    }
}
