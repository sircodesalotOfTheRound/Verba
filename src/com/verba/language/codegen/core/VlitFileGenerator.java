package com.verba.language.codegen.core;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.codegen.classes.VlitClassGenerator;
import com.verba.language.codegen.functions.VlitFunctionGenerator;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.expressions.tags.aspect.AspectTagExpression;
import com.verba.language.expressions.tags.hashtag.HashTagExpression;
import com.verba.language.symbols.resolution.polymorphic.PolymorphicResolver;
import com.verba.language.symbols.table.entries.SymbolTableEntry;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;

/**
 * The vlit class generator does all of the cordinating work to create the final
 * vlit file.
 */
public class VlitFileGenerator {
    private final StaticSpaceExpression root;
    private final GlobalSymbolTable symbolTable;
    private final Partition<Class, SymbolTableEntry> expressions;
    private QIterable<VlitFunctionGenerator> functions;

    public VlitFileGenerator(StaticSpaceExpression root) {
        this.root = root;
        this.symbolTable = root.globalSymbolTable();
        this.expressions = this.buildGrouping();

        this.process();
    }

    private void process() {
        this.functions = this.processFunctions();
    }

    // Group the symbol table items by their type.
    private Partition<Class, SymbolTableEntry> buildGrouping() {
        // Group by the type of each symbol table item.
        return this.symbolTable
            .entries()
            .parition(entry -> entry.instance().getClass());
    }

    private <T> QIterable<SymbolTableEntry> get(Class<T> type) {
        return this.expressions.get(type);
    }

    private <T> boolean containsKey(Class<T> type) {
        return this.expressions.get(type) != null;
    }

    public void processMetatags() {
        for (SymbolTableEntry metatagEntry : this.get(HashTagExpression.class)) {

        }

        for (SymbolTableEntry metatagEntry : this.get(AspectTagExpression.class)) {

        }
    }

    public Object processClasses() {
        if (!this.containsKey(ClassDeclarationExpression.class)) return null;

        // Resolve all of the members associated to each class.
        // This automatically caches the member information to the entry
        // in the symbol table (as an instance of PolymorphicResolutionMetadata)
        PolymorphicResolver resolver = new PolymorphicResolver(symbolTable);
        for (SymbolTableEntry classDeclaration : this.get(ClassDeclarationExpression.class)) {

            VlitClassGenerator generator = new VlitClassGenerator(resolver, classDeclaration);
        }

        // TODO: Return something
        return null;
    }

    public QIterable<VlitFunctionGenerator> processFunctions() {
        if (!containsKey(FunctionDeclarationExpression.class)) return null;

        return this.get(FunctionDeclarationExpression.class)
            .map(functionEntry -> new VlitFunctionGenerator(functionEntry))
            .toList();
    }

    public StaticSpaceExpression root() {
        return this.root;
    }

    public GlobalSymbolTable symbolTable() {
        return this.symbolTable;
    }

    public boolean hasFunctions() {
        return this.functions != null;
    }

    public QIterable<VlitFunctionGenerator> functions() {
        return this.functions;
    }
}
