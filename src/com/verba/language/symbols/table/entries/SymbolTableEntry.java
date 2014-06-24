package com.verba.language.symbols.table.entries;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-3-9.
 */
public class SymbolTableEntry {
    private final String name;
    private final ScopedSymbolTable table;
    private final VerbaExpression object;
    private final QList<SymbolTableMetadata> metadata = new QList<>();

    public SymbolTableEntry(String name, ScopedSymbolTable table, VerbaExpression object, SymbolTableMetadata... metadata) {
        this.name = name;
        this.table = table;
        this.object = object;

        for (SymbolTableMetadata item : metadata) {
            this.metadata.add(item);
        }
    }

    public String name() {
        return this.name;
    }

    public ScopedSymbolTable table() {
        return this.table;
    }

    public VerbaExpression instance() {
        return this.object;
    }

    public <T> T instanceAs(Class<T> type) {
        return (T) this.object;
    }

    public Class type() {
        return this.object.getClass();
    }

    public void addMetadata(SymbolTableMetadata metadata) {
        this.metadata.add(metadata);
    }

    public QIterable<SymbolTableMetadata> metadata() {
        return this.metadata;
    }

    public String fqn() {
        if (table.fqn() != null) return String.format("%s.%s", table.fqn(), name);
        else return name;
    }


    public <T> boolean is(Class<T> type) {
        return type.isAssignableFrom(object.getClass());
    }
}
