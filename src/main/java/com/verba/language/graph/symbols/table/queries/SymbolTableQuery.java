package com.verba.language.graph.symbols.table.queries;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 14/12/6.
 */
public class SymbolTableQuery {
  public String fqn;
  public QList<String> genericParameterTypes;
  public QList<String> parameterTypes;

  public SymbolTableQuery(String fqn) {
    this.fqn = fqn;
    this.genericParameterTypes = new QList<>();
    this.parameterTypes = new QList<>();
  }

  public QIterable<String> genericParameterFqns() { return this.genericParameterTypes; }
  public QIterable<String> parameterFqns() { return this.parameterTypes; }

  public SymbolTableQuery addGenericParameterFqn(String type) {
    this.genericParameterTypes.add(type);

    return this;
  }

  public SymbolTableQuery addParamterFqn(String type) {
    this.parameterTypes.add(type);

    return this;
  }
}
