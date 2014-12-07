package com.verba.language.graph.symbols.table.queries;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 14/12/6.
 */
public class SymbolTableQuery {
  public String fqn;
  public QList<String> genericParameterFqns;
  public QList<String> parameterFqns;

  public SymbolTableQuery(String fqn) {
    this.fqn = fqn;
    this.genericParameterFqns = new QList<>();
    this.parameterFqns = new QList<>();
  }

  public QIterable<String> genericParameterFqns() { return this.genericParameterFqns; }
  public QIterable<String> parameterFqns() { return this.parameterFqns; }

  public void addGenericParameterFqn(String fqn) {
    this.genericParameterFqns.add(fqn);
  }

  public void addParamterFqn(String fqn) {
    this.parameterFqns.add(fqn);
  }
}
