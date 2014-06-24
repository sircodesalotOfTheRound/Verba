package com.verba.test.symbol.resolution;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.expressions.statements.declaration.MutaDeclarationStatement;
import com.verba.language.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.lexing.Lexer;
import com.verba.language.symbols.meta.NestedSymbolTableMetadata;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.symbols.table.tables.ScopedSymbolTable;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-7.
 */
public class ObjectResolutionTesting {
    private static ClassDeclarationExpression readAst() throws Exception {
        Lexer lexer = GeneralLexing.generateFromFileInThisPackage("ObjectResolutionTesting.v");
        return (ClassDeclarationExpression) VerbaExpression.read(null, lexer);
    }

    @Test
    public void objectResolutionTesting() throws Exception {
        ClassDeclarationExpression declaration = ObjectResolutionTesting.readAst();
        ScopedSymbolTable classTable = new ScopedSymbolTable(declaration);

        assert (classTable.entries().count() == 5);
        assert (classTable.get("T").first(t -> t.table() == classTable).instance() instanceof VarNameDeclarationExpression);
        assert (classTable.get("function").single().instance() instanceof FunctionDeclarationExpression);
        assert (classTable.get("item").single().instance() instanceof ValDeclarationStatement);
        assert (classTable.get("outerObject").single().instance() instanceof ValDeclarationStatement);

        assert (classTable.get("item").single().fqn().equals("OuterClass.item"));
        assert (classTable.get("outerObject").single().fqn().equals("OuterClass.outerObject"));

        // Function table testing
        SymbolTableEntry classTableFunctionEntry = classTable.get("function").single();
        QIterable<NestedSymbolTableMetadata> nestedTableMetadata
            = classTableFunctionEntry.metadata().ofType(NestedSymbolTableMetadata.class);

        ScopedSymbolTable functionTable = nestedTableMetadata.single().symbolTable();

        assert (functionTable.entries().count() == 5);

        // General validation
        assert (functionTable.get("U").single().instance() instanceof VarNameDeclarationExpression);
        assert (functionTable.get("param").single().instance() instanceof VarNameDeclarationExpression);
        assert (functionTable.get("innerObject").single().instance() instanceof ValDeclarationStatement);
        assert (functionTable.get("anotherObject").single().instance() instanceof MutaDeclarationStatement);
        assert (functionTable.get("item").single().instance() instanceof MutaDeclarationStatement);

        // Test innerObject
        ValDeclarationStatement innerObject = functionTable
            .entries()
            .single(entry -> entry.name().equals("innerObject"))
            .instanceAs(ValDeclarationStatement.class);

        VarNameDeclarationExpression innerObjectRval = (VarNameDeclarationExpression) innerObject.rvalue();
        SymbolTableEntry innerObjectResolution
            = functionTable.resolveName(innerObjectRval.representation()).single();

        assert (innerObjectResolution.table().name().equals("OuterClass"));

        // Test anotherObject
        MutaDeclarationStatement anotherObject = functionTable
            .entries()
            .single(entry -> entry.name().equals("anotherObject"))
            .instanceAs(MutaDeclarationStatement.class);

        VarNameDeclarationExpression anotherObjectRval = (VarNameDeclarationExpression) anotherObject.rvalue();
        SymbolTableEntry anotherObjectRvalNameResolution
            = functionTable.resolveName(anotherObjectRval.representation()).single();

        // Ensure that the class that this name resolves to is the outer class.
        assert (anotherObjectRvalNameResolution.table().name().equals("function"));

        // Test item
        MutaDeclarationStatement item = functionTable
            .entries()
            .single(entry -> entry.name().equals("item"))
            .instanceAs(MutaDeclarationStatement.class);

        FullyQualifiedNameExpression itemType = (FullyQualifiedNameExpression) item.typeDeclaration();
        SymbolTableEntry itemTypeResolution
            = functionTable.resolveName(itemType.representation())
            .first(t -> t.table() == classTable);

        assert (itemTypeResolution.table() == classTable);

        assert (functionTable.get("innerObject").single().fqn().equals("OuterClass.function.innerObject"));
        assert (functionTable.get("anotherObject").single().fqn().equals("OuterClass.function.anotherObject"));
        assert (functionTable.get("item").single().fqn().equals("OuterClass.function.item"));

        // Look up all resolutions for 'T'
        assert (functionTable.resolveName("T").count() == 2);
        assert (functionTable.resolveName("T").single(t -> t.is(VarNameDeclarationExpression.class)) != null);
        assert (functionTable.resolveName("T").single(t -> t.is(ValDeclarationStatement.class)) != null);
    }


    @Test
    public void masterTableTesting() throws Exception {
        ClassDeclarationExpression declaration = ObjectResolutionTesting.readAst();
        GlobalSymbolTable table = new GlobalSymbolTable(declaration);

        assert (table.getByFqn("OuterClass.T").count() == 2);
        assert (table.getByFqn("OuterClass.function") != null);
        assert (table.getByFqn("OuterClass.item") != null);
        assert (table.getByFqn("OuterClass.outerObject") != null);
        assert (table.getByFqn("OuterClass.function.U") != null);
        assert (table.getByFqn("OuterClass.function.param") != null);
        assert (table.getByFqn("OuterClass.function.innerObject") != null);
        assert (table.getByFqn("OuterClass.function.anotherObject") != null);
        assert (table.getByFqn("OuterClass.function.item") != null);
    }
}
