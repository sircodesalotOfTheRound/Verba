package com.verba.language.graph.symbols.table.entries;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

import java.io.Serializable;

/**
 * Created by sircodesalot on 14-3-9.
 */
public class SymbolTableEntry implements Serializable {
  private final String name;
  private final ScopedSymbolTable table;
  private final VerbaExpression object;
  private final VerbaCodePage page;
  private final String fqn;
  private final QList<SymbolTableMetadata> metadata = new QList<>();

  public SymbolTableEntry(String name, ScopedSymbolTable table, VerbaExpression object, SymbolTableMetadata... metadata) {
    this.name = name;
    this.table = table;
    this.object = object;
    this.page = discoverPage(object);
    this.fqn = determineFqn();

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

  public QIterable<SymbolTableMetadata> metadata() { return this.metadata; }

  public VerbaCodePage page() { return this.page; }

  private VerbaCodePage discoverPage(VerbaExpression object) {
    if (object.is(VerbaCodePage.class)) {
      return (VerbaCodePage)object;
    } else {
      return discoverPage(object.parent());
    }
  }

  private String determineFqn() {
    if (!table.fqn().isEmpty()) return String.format("%s.%s", table.fqn(), name);
    else return name;
  }

  public String fqn() { return this.fqn; }

  public <T> boolean is(Class<T> type) {
    return type.isAssignableFrom(object.getClass());
  }
}
