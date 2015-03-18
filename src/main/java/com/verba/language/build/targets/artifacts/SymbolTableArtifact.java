package com.verba.language.build.targets.artifacts;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;
import com.verba.tools.datastructures.OneToManyMap;
import com.verba.tools.polymorphism.ClassHierarchyFlattener;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolTableArtifact implements BuildArtifact {
  private final SymbolTable symbolTable;
  private final ClassHierarchyToObjectMap symbolsByType;

  public SymbolTableArtifact(VerbaSourceCodeFile page) {
    this.symbolTable = new SymbolTable(page);
    this.symbolsByType = partitionSymbolsByType(symbolTable);
  }

  // Associates a class, and its entire hierarchy to an object.
  public static class ClassHierarchyToObjectMap {
    private final OneToManyMap<Class, Symbol> map = new OneToManyMap<>();

    public void addSymbol(Symbol symbol) {
      ClassHierarchyFlattener hierarchy = new ClassHierarchyFlattener(symbol.type());
      for (Class iface : hierarchy.flattenedHierarchy()) {
        map.add(iface, symbol);
      }
    }

    public QIterable<Symbol> getDerivedClassesForInterface(Class type) {
      return map.getItemsForKey(type);
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
