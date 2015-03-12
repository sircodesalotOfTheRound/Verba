package com.verba.language.build.event.subscriptions;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.configuration.BuildProfile;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventSubscription;
import com.verba.language.graph.symbols.resolution.PolymorphicDeclarationNameResolver;
import com.verba.language.graph.symbols.resolution.SymbolNameResolver;
import com.verba.language.graph.symbols.resolution.SymbolResolutionMatch;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class PolymorphicBuildEventHandler extends BuildEventSubscription<PolymorphicDeclarationExpression>
  implements BuildEvent.NotifySymbolTableBuildEvent,
  BuildEvent.NotifyCodeGenerationEvent
{
  private SymbolTable symbolTable;
  private Symbol thisEntry;
  private QIterable<Symbol> traitEntries;
  private Partition<String, Symbol> traitEntriesByName;
  private QIterable<Symbol> immediateMembers;
  private QIterable<Symbol> allMembers;
  private Partition<String, Symbol> symbolTableEntriesByName;

  public PolymorphicBuildEventHandler(PolymorphicDeclarationExpression expression) {
    super(expression);

  }

  @Override
  public void beforeSymbolsGenerated(BuildProfile analysis, LitFileRootExpression buildAnalysis) { }

  @Override
  public void afterSymbolsGenerated(BuildProfile buildProfile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  @Override
  public void onResolveSymbols(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.thisEntry = symbolTable.findByInstance(this.expression());
    this.immediateMembers = determineImmediateMembers(this.expression());
    this.allMembers = determineAllMembers(this.expression(), new QList<>());
    this.symbolTableEntriesByName = this.allMembers.parition(Symbol::name);
    this.traitEntriesByName = this.traitEntriesByName();
  }

  private QIterable<Symbol> determineTraitEntries(SymbolTable symbolTable) {
    Scope scope = symbolTable.resolveScope(this.expression());

    SymbolNameResolver nameResolver = new SymbolNameResolver(symbolTable, scope);
    QList<Symbol> entriesForTraits = new QList<>() ;
    for (TypeConstraintExpression expression : this.expression().traits()) {
      SymbolResolutionMatch match = nameResolver.findSymbolsInScope(expression.representation()).first();
      entriesForTraits.add(match.symbol());
    }

    return entriesForTraits;
  }

  public QIterable<Symbol> traitEntries() {
    if (this.traitEntries == null) {
      this.traitEntries = determineTraitEntries(symbolTable);
    }

    return this.traitEntries;
  }

  @Override
  public void beforeCodeGeneration(BuildProfile buildProfile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }

  public QIterable<Symbol> immediateMembers() { return this.immediateMembers; }
  public QIterable<Symbol> allMembers() { return this.allMembers; }
  public QIterable<Symbol> findMembersByName(String name) {
    if (this.symbolTableEntriesByName.containsKey(name)) {
      return this.symbolTableEntriesByName.get(name);
    }

    return new QList<>();
  }

  private Partition<String, Symbol> traitEntriesByName() {
    if (this.traitEntriesByName == null) {
      this.traitEntriesByName = this.traitEntries().parition(Symbol::fqn);
    }

    return this.traitEntriesByName;
  }

  public boolean isMember(String name) {
    return this.symbolTableEntriesByName.containsKey(name);
  }

  public boolean isImmediateMember(String name) {
    if (isMember(name)) {
      return this.symbolTableEntriesByName.get(name).any(entry -> entry.scope() == this.thisEntry.scope());
    }

    return false;
  }

  private QIterable<Symbol> determineImmediateMembers(PolymorphicDeclarationExpression expression) {
    PolymorphicDeclarationNameResolver members = new PolymorphicDeclarationNameResolver(this.symbolTable, expression);
    return members.immediateMembers();
  }

  private QIterable<Symbol> determineAllMembers(PolymorphicDeclarationExpression expression, QList<Symbol> names) {
    QIterable<PolymorphicDeclarationExpression> traits = expression.traitSymbolTableEntries()
      .map(Symbol::expression)
      .cast(PolymorphicDeclarationExpression.class);

    PolymorphicDeclarationNameResolver members = new PolymorphicDeclarationNameResolver(this.symbolTable, expression);
    names.add(members.immediateMembers());

    for (PolymorphicDeclarationExpression trait : traits) {
      determineAllMembers(trait, names);
    }

    return names;
  }

  public boolean isDerivedFrom(String name) {
    return this.traitEntriesByName().containsKey(name);
  }
}
