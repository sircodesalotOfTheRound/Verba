package com.verba.language.build.targets.artifacts;

import com.javalinq.implementations.QList;
import com.javalinq.implementations.QMap;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolTableArtifact implements BuildArtifact {
  private final SymbolTable symbolTable;
  private final ClassListMap symbolsByType;

  public SymbolTableArtifact(VerbaCodePage page) {
    this.symbolTable = new SymbolTable(page);
    this.symbolsByType = partitionSymbolsByType(symbolTable);
  }

  // Keeps a list of symbols associated with each class type.
  public static class ClassListMap {
    private final QMap<Class, QList<Symbol>> map = new QMap<>();

    public void addSymbol(Symbol symbol) {
      QIterable<Class> interfaces = collectInterfacesForType(symbol);
      for (Class iface : interfaces) {
        add(iface, symbol);
      }
    }

    private void add(Class type, Symbol symbol) {
      QList<Symbol> list = getListForClass(type);
      list.add(symbol);
    }

    private QList<Symbol> getListForClass(Class type) {
      if (map.containsKey(type)) {
        return map.get(type);
      } else {
        QList<Symbol> list = new QList<>();
        map.add(type, list);
        return list;
      }
    }

    private QIterable<Class> collectInterfacesForType(Symbol symbol) {
      return collectInterfacesForType(symbol.type(), new QList<>());
    }

    private QIterable<Class> collectInterfacesForType(Class type, QList<Class> interfaces) {
      if (type == null) return interfaces;

      collectInterfacesForType(type.getSuperclass(), interfaces);
      for (Class iface : type.getInterfaces()) {
        collectInterfacesForType(iface, interfaces);
      }

      interfaces.add(type);
      return interfaces;
    }


    public QIterable<Symbol> getList(Class type) {
      return map.get(type);
    }

    public boolean containsListForType(Class type) {
      return map.containsKey(type);
    }
  }

  public SymbolTableArtifact(LitFileSyntaxTreeArtifact syntaxTree) {
    this.symbolTable = new SymbolTable(syntaxTree.expression());
    this.symbolsByType = partitionSymbolsByType(symbolTable);
  }

  private ClassListMap partitionSymbolsByType(SymbolTable symbolTable) {
    ClassListMap map = new ClassListMap();
    for (Symbol symbol : symbolTable.entries()) {
      map.addSymbol(symbol);
    }

    return map;
  }

  public SymbolTable symbolTable() { return this.symbolTable; }

  public <T> boolean containsSymbolsOfType(Class<T> type) {
    return symbolsByType.containsListForType(type);
  }

  public <T extends VerbaExpression> QIterable<T> getSymbolsOfType(Class<T> type) {
    return symbolsByType.getList(type).cast(type);
  }
}
