package com.verba.language.graph.symbols.resolution;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.categories.NamedExpression;
import sun.jvm.hotspot.debugger.cdbg.Sym;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class PolymorphicDeclarationMemberResolver {
  private final PolymorphicDeclarationExpression declaration;
  private QIterable<Symbol> members;
  private Partition<String, Symbol> membersByName;

  public PolymorphicDeclarationMemberResolver(PolymorphicDeclarationExpression declaration) {
    this.declaration = declaration;
  }

  public QIterable<Symbol> determineNamesInScope(SymbolTable symbolTable) {
    return declaration.block().expressions()
      .ofType(NamedExpression.class)
      .map(expression -> symbolTable.findByInstance((VerbaExpression) expression))
      .toList();
  }

  public PolymorphicDeclarationExpression declaration() { return declaration; }
  public QIterable<Symbol> immediateMembers() { return this.members; }

  public boolean containsImmediateMemberWithName(String name) { return this.membersByName.containsKey(name); }
  public QIterable<Symbol> findImmediateMembersByName(String name) { return this.membersByName.get(name); }

  public void resolve(SymbolTable table) {
    this.members = determineNamesInScope(table);
    this.membersByName = this.members.parition(Symbol::name);
  }
}
