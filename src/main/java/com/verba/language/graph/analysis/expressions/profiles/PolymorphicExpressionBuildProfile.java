package com.verba.language.graph.analysis.expressions.profiles;

import com.javalinq.implementations.QList;
import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.analysis.expressions.tools.BuildProfile;
import com.verba.language.graph.symbols.resolution.SymbolNameResolver;
import com.verba.language.graph.symbols.resolution.SymbolResolutionMatch;
import com.verba.language.graph.symbols.resolution.TraitDeclarationNameResolver;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.parsing.expressions.categories.PolymorphicExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;

/**
 * Created by sircodesalot on 14/11/24.
 */
public class PolymorphicExpressionBuildProfile extends BuildProfile<ClassDeclarationExpression> {
  private GlobalSymbolTable symbolTable;
  private SymbolTableEntry thisEntry;
  private QIterable<SymbolTableEntry> traitEntries;
  private QIterable<SymbolTableEntry> immediateMembers;
  private QIterable<SymbolTableEntry> allMembers;
  private Partition<String, SymbolTableEntry> symbolTableEntriesByName;

  public PolymorphicExpressionBuildProfile(ClassDeclarationExpression expression) {
    super(expression);

  }

  @Override
  public void afterParse(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) {

  }

  @Override
  public void beforeSymbolTableAssociation(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) {

  }

  @Override
  public void afterSymbolTableAssociation(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.thisEntry = symbolTable.getByInstance(this.expression);
    this.traitEntries = determineTraitEntries(symbolTable);
    this.immediateMembers = determineImmediateMembers(this.expression);
    this.allMembers = determineAllMembers(this.expression, new QList<>());
    this.symbolTableEntriesByName = this.allMembers.parition(SymbolTableEntry::name);
  }

  private QIterable<SymbolTableEntry> determineTraitEntries(GlobalSymbolTable symbolTable) {
    SymbolNameResolver nameResolver = new SymbolNameResolver(symbolTable, this.thisEntry.table());

    QList<SymbolTableEntry> entriesForTraits = new QList<>() ;
    for (TypeDeclarationExpression expression : this.expression.traits()) {
      SymbolResolutionMatch match = nameResolver.findSymbolsInScope(expression.representation()).first();
      entriesForTraits.add(match.entry());
    }

    return entriesForTraits;
  }

  public QIterable<SymbolTableEntry> traitEntries() { return this.traitEntries; }

  @Override
  public void beforeCodeGeneration(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {

  }

  public QIterable<SymbolTableEntry> immediateMembers() { return this.immediateMembers; }
  public QIterable<SymbolTableEntry> allMembers() { return this.allMembers; }

  public QIterable<SymbolTableEntry> findMembersByName(String name) {
    return this.symbolTableEntriesByName.get(name);
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

  private QIterable<SymbolTableEntry> determineImmediateMembers(PolymorphicExpression expression) {
    TraitDeclarationNameResolver members = new TraitDeclarationNameResolver(this.symbolTable, expression);
    return members.immediateMembers();
  }

  private QIterable<SymbolTableEntry> determineAllMembers(PolymorphicExpression expression, QList<SymbolTableEntry> names) {
    QIterable<PolymorphicExpression> traits = expression.traitSymbolTableEntries()
      .map(entry -> entry.instanceAs(PolymorphicExpression.class));

    TraitDeclarationNameResolver members = new TraitDeclarationNameResolver(this.symbolTable, expression);
    names.add(members.immediateMembers());

    for (PolymorphicExpression trait : traits) {
      determineAllMembers(trait, names);
    }

    return names;
  }

}
