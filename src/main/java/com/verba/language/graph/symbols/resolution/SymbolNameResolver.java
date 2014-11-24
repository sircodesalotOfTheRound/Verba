package com.verba.language.graph.symbols.resolution;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.parsing.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class SymbolNameResolver {
  private final String EMPTY_NAMESPACE = "";
  private final GlobalSymbolTable symbolTable;
  private final ScopedSymbolTable scope;
  private final VerbaCodePage page;
  private final QIterable<String> namespacesInScope;

  public SymbolNameResolver(GlobalSymbolTable symbolTable, ScopedSymbolTable scope) {
    this.symbolTable = symbolTable;
    this.scope = scope;
    this.page = discoverPage(scope);
    this.namespacesInScope = namespacesInScope(page, scope);
  }

  public QIterable<SymbolResolutionMatch> findSymbolsInScope(String name) {
    QList<SymbolResolutionMatch> matchingEntries = new QList<>();

    String fqn;
    for (String namespace : namespacesInScope) {
      if (!namespace.isEmpty()) {
        fqn = String.format("%s.%s", namespace, name);
      } else {
        fqn = name;
      }

      for (SymbolTableEntry entry : symbolTable.getByFqn(fqn)) {
        SymbolResolutionMatch match = new SymbolResolutionMatch(entry);
        matchingEntries.add(match);
      }
    }

    return matchingEntries;
  }

  private VerbaCodePage discoverPage(ScopedSymbolTable scope) {
    if (scope.entries().any()) {
      return scope.entries().first().page();
    } else {
      return discoverPage((VerbaCodePage)scope.header());
    }
  }

  private VerbaCodePage discoverPage(VerbaExpression expression) {
    if (expression instanceof VerbaCodePage) {
      return (VerbaCodePage) expression;
    } else {
      return discoverPage(expression.parent());
    }
  }

  private QIterable<String> namespacesInScope(VerbaCodePage page, ScopedSymbolTable scope) {
    QList<String> namespaces = new QList<>();

    namespaces.add(scope.fqnList());
    namespaces.add(page.importedNamespaces());
    namespaces.add(EMPTY_NAMESPACE);

    return namespaces;
  }
}
