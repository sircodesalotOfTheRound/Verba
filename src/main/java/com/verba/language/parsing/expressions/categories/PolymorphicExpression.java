package com.verba.language.parsing.expressions.categories;


import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;

/**
 * Implemented on ClassDeclarationExpression and TraitDeclaration expression.
 * Means that this class can derive from other classes / traits.
 */
public interface PolymorphicExpression {
  QIterable<SymbolTableEntry> traitSymbolTableEntries();
  QIterable<TypeDeclarationExpression> traits();
  QIterable<SymbolTableEntry> allMembers();
  QIterable<SymbolTableEntry> immediateMembers();

  boolean isMember(String name);
  boolean isImmediateMember(String name);
  QIterable<SymbolTableExpression> findMembersByName(String name);

  boolean hasTraits();
  BlockDeclarationExpression block();
}
