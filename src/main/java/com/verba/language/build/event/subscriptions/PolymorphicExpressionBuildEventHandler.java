package com.verba.language.build.event.subscriptions;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.BuildAnalysis;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.ExpressionBuildEventSubscription;
import com.verba.language.graph.symbols.resolution.PolymorphicDeclarationNameResolver;
import com.verba.language.graph.symbols.resolution.SymbolNameResolver;
import com.verba.language.graph.symbols.resolution.SymbolResolutionMatch;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class PolymorphicExpressionBuildEventHandler extends ExpressionBuildEventSubscription<PolymorphicDeclarationExpression>
  implements BuildEvent.NotifySymbolTableBuildEvent,
  BuildEvent.NotifyCodeGenerationEvent
{
  private GlobalSymbolTable symbolTable;
  private Symbol thisEntry;
  private QIterable<Symbol> traitEntries;
  private Partition<String, Symbol> traitEntriesByName;
  private QIterable<Symbol> immediateMembers;
  private QIterable<Symbol> allMembers;
  private Partition<String, Symbol> symbolTableEntriesByName;

  public PolymorphicExpressionBuildEventHandler(PolymorphicDeclarationExpression expression) {
    super(expression);

  }

  @Override
  public void beforeSymbolTableAssociation(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) { }

  @Override
  public void afterSymbolTableAssociation(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.thisEntry = symbolTable.getByInstance(this.expression());
    this.traitEntries = determineTraitEntries(symbolTable);
    this.traitEntriesByName = this.traitEntries.parition(Symbol::fqn);
    this.immediateMembers = determineImmediateMembers(this.expression());
    this.allMembers = determineAllMembers(this.expression(), new QList<>());
    this.symbolTableEntriesByName = this.allMembers.parition(Symbol::name);
  }

  private QIterable<Symbol> determineTraitEntries(GlobalSymbolTable symbolTable) {
    SymbolNameResolver nameResolver = new SymbolNameResolver(symbolTable, this.thisEntry.table());

    QList<Symbol> entriesForTraits = new QList<>() ;
    for (TypeDeclarationExpression expression : this.expression().traits()) {
      SymbolResolutionMatch match = nameResolver.findSymbolsInScope(expression.representation()).first();
      entriesForTraits.add(match.entry());
    }

    return entriesForTraits;
  }

  public QIterable<Symbol> traitEntries() { return this.traitEntries; }

  @Override
  public void beforeCodeGeneration(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {

  }

  public QIterable<Symbol> immediateMembers() { return this.immediateMembers; }
  public QIterable<Symbol> allMembers() { return this.allMembers; }
  public QIterable<Symbol> findMembersByName(String name) {
    if (this.symbolTableEntriesByName.containsKey(name)) {
      return this.symbolTableEntriesByName.get(name);
    }

    return new QList<>();
  }

  public boolean isMember(String name) {
    return this.symbolTableEntriesByName.containsKey(name);
  }

  public boolean isImmediateMember(String name) {
    if (isMember(name)) {
      return this.symbolTableEntriesByName.get(name).any(entry -> entry.table() == this.thisEntry.table());
    }

    return false;
  }

  private QIterable<Symbol> determineImmediateMembers(PolymorphicDeclarationExpression expression) {
    PolymorphicDeclarationNameResolver members = new PolymorphicDeclarationNameResolver(this.symbolTable, expression);
    return members.immediateMembers();
  }

  private QIterable<Symbol> determineAllMembers(PolymorphicDeclarationExpression expression, QList<Symbol> names) {
    QIterable<PolymorphicDeclarationExpression> traits = expression.traitSymbolTableEntries()
      .map(entry -> entry.instanceAs(PolymorphicDeclarationExpression.class));

    PolymorphicDeclarationNameResolver members = new PolymorphicDeclarationNameResolver(this.symbolTable, expression);
    names.add(members.immediateMembers());

    for (PolymorphicDeclarationExpression trait : traits) {
      determineAllMembers(trait, names);
    }

    return names;
  }

  public boolean isDerivedFrom(String name) {
    return this.traitEntriesByName.containsKey(name);
  }
}
