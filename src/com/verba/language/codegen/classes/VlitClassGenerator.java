package com.verba.language.codegen.classes;

import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.symbols.resolution.polymorphic.PolymorphicResolutionMetadata;
import com.verba.language.symbols.resolution.polymorphic.PolymorphicResolver;
import com.verba.language.symbols.table.entries.SymbolTableEntry;

/**
 * Generates vlit information that describes a class.
 */
public class VlitClassGenerator {
    private final SymbolTableEntry entry;
    private final ClassDeclarationExpression declaration;
    private PolymorphicResolutionMetadata resolutionMetadata;

    public VlitClassGenerator(PolymorphicResolver resolver, SymbolTableEntry entry) {

        this.entry = entry;
        this.declaration = (ClassDeclarationExpression) entry.instance();

        this.resolveMembers(resolver, entry);
        this.buildImage();
    }

    private void resolveMembers(PolymorphicResolver resolver, SymbolTableEntry entry) {
        this.resolutionMetadata = resolver.resolveBinding(entry);
    }

    private void buildImage() {

    }
}
