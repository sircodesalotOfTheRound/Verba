package com.verba.language.symbols.meta;

import com.verba.language.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.symbols.table.tables.ScopedSymbolTable;

/**
 * This is for items in a symbol table that themselves are a symbol table block.
 */
public class NestedSymbolTableMetadata implements SymbolTableMetadata {
    private final ScopedSymbolTable nestedTable;

    public NestedSymbolTableMetadata(ScopedSymbolTable nestedTable) {
        this.nestedTable = nestedTable;
    }

    public ScopedSymbolTable symbolTable() {
        return this.nestedTable;
    }

}
