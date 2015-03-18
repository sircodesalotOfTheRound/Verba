package com.verba.language.build.targets.artifacts;

import com.javalinq.implementations.QList;
import com.javalinq.implementations.QMap;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.testtools.polymorphism.ClassHierarchyFlattener;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolTableArtifact implements BuildArtifact {
  private final SymbolTable symbolTable;
  private final ClassHierarchyToObjectMap symbolsByType;

  public SymbolTableArtifact(VerbaCodePage page) {
    this.symbolTable = new SymbolTable(page);
    this.symbolsByType = partitionSymbolsByType(symbolTable);
  }

  // Associates a class, and its entire hierarchy to an object.
  public static class ClassHierarchyToObjectMap<TObject> {
    private final QMap<Class, QList<Symbol>> map = new QMap<>();

    public void addSymbol(Symbol symbol) {
      ClassHierarchyFlattener hierarchy = new ClassHierarchyFlattener(symbol.type());
      for (Class iface : hierarchy.flattenedHierarchy()) {
        QList<Symbol> list = getListForInterface(iface);
        list.add(symbol);
      }
    }

    private QList<Symbol> getListForInterface(Class type) {
      if (map.containsKey(type)) {
        return map.get(type);
      } else {
        QList<Symbol> list = new QList<>();
        map.add(type, list);
        return list;
      }
    }

    public QIterable<Symbol> getDerivedClassesForInterface(Class type) {
      return map.get(type);
    }
    public boolean containsInterface(Class type) {
      return map.containsKey(type);
    }
  }

  public SymbolTableArtifact(LitFileSyntaxTreeArtifact syntaxTree) {
    this.symbolTable = new SymbolTable(syntaxTree.expression());
    this.symbolsByType = partitionSymbolsByType(symbolTable);
  }

  private ClassHierarchyToObjectMap partitionSymbolsByType(SymbolTable symbolTable) {
    ClassHierarchyToObjectMap map = new ClassHierarchyToObjectMap();
    for (Symbol symbol : symbolTable.entries()) {
      map.addSymbol(symbol);
    }

    return map;
  }

  public SymbolTable symbolTable() { return this.symbolTable; }

  public <T> boolean containsSymbolsOfType(Class<T> type) {
    return symbolsByType.containsInterface(type);
  }

  public <T extends VerbaExpression> QIterable<T> getSymbolsOfType(Class<T> type) {
    return symbolsByType.getDerivedClassesForInterface(type).cast(type);
  }
}
