package com.verba.language.graph.symbols.resolution.polymorphic;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.categories.PolymorphicExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.graph.symbols.meta.NestedSymbolTableMetadata;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.resolution.interfaces.SymbolResolutionInfo;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.graph.symbols.table.tables.SimpleSymbolTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sircodesalot on 14-5-20.
 */
public class PolymorphicResolutionMetadata implements SymbolResolutionInfo, SymbolTableMetadata {
  private final Set<FullyQualifiedNameExpression> unresolvableTraits = new HashSet<>();
  private final Set<SymbolTableEntry> seenBaseTraits = new HashSet<>();
  private final GlobalSymbolTable globalSymbolTable;
  private final SimpleSymbolTable visibleMembersSymbolTable = new SimpleSymbolTable();
  private final SimpleSymbolTable immediateMembers = new SimpleSymbolTable();
  private boolean containsInheritanceLoop = false;

  public PolymorphicResolutionMetadata(GlobalSymbolTable symbolTable, SymbolTableEntry entry) {
    this.globalSymbolTable = symbolTable;

    this.addItemsFromSymbolTable(entry, true);
  }

  private void addItemsFromSymbolTable(SymbolTableEntry traitEntry, boolean areImmediateMembers) {
    ScopedSymbolTable symbolTableForTraitEntry = this.getSymbolTableForClass(traitEntry);
    PolymorphicExpression trait = traitEntry.instanceAs(PolymorphicExpression.class);

    // Add the entry to the seen-set to ensure we haven't seen it before (inheritance loops).
    if (seenBaseTraits.add(traitEntry)) {

      // If the object has sub traits, process those.
      if (trait.hasTraits()) {
        this.visitSubTraits(trait);
      }

      // Then process this item
      for (SymbolTableEntry scopedEntry : symbolTableForTraitEntry.entries()) {
        // Add to the master list of all visible members.
        // Then add to the immediate member list if areImmediateMembers is true.
        this.visibleMembersSymbolTable.add(scopedEntry);
        if (areImmediateMembers) this.immediateMembers.add(scopedEntry);
      }

    } else {
      this.containsInheritanceLoop = true;
    }

  }

  // Classes/Traits always get their own ScopedSymbolTable (Just like anything else with a block).
  // A Reference to which is contained in the 'metadata' field of it's own symbol table entry.
  private ScopedSymbolTable getSymbolTableForClass(SymbolTableEntry entry) {
    NestedSymbolTableMetadata blockItem = (NestedSymbolTableMetadata) entry.metadata()
      .single(metadata -> metadata instanceof NestedSymbolTableMetadata);

    return blockItem.symbolTable();
  }

  private void visitSubTraits(PolymorphicExpression declaration) {
    // Grab all of the traits from the class declaration
    // Since the traits should have been pre-validated,
    // we can assume they are all now 'FullyQualifiedNameExpressions'.
    QIterable<FullyQualifiedNameExpression> traitNames
      = declaration.traits().cast(FullyQualifiedNameExpression.class);

    // For each trait, locate them on the symbol table,
    // Then add each declaration to this symbol table.
    for (FullyQualifiedNameExpression traitName : traitNames) {
      // Get the symbol tables entries associated with the trait
      QIterable<SymbolTableEntry> matchingEntries = globalSymbolTable.getByFqn(traitName.representation());

      // Verify that the trait could be resolved. If null or duplicate name found. Add fqn as violation.
      if (matchingEntries == null || matchingEntries.count() > 1) {
        this.unresolvableTraits.add(traitName);
        break;

      } else {
        // Capture the matching entry name.
        SymbolTableEntry subTrait = matchingEntries.single();

        // Check if the subtrait is pre-cached (that is, it is a subclass that has
        // already been processed previously). If so, just add all of it's members
        // and return. Else walk to the subclass and continue processing.
        if (addPreCachedSubtrait(subTrait)) continue;
        else this.addItemsFromSymbolTable(matchingEntries.single(), false);
      }
    }
  }

  private boolean addPreCachedSubtrait(SymbolTableEntry subTrait) {
    // Check if the subTrait that we're adding has already been
    // processed and cached. If so, then we'll go ahead and add it.
    PolymorphicResolutionMetadata cachedMetadata = subTrait.metadata()
      .ofType(PolymorphicResolutionMetadata.class)
      .singleOrNull();

    // If the entry hasn't already been precached, then return false. (not precached).
    if (cachedMetadata == null) return false;

    // Add all of it's sub-members
    for (SymbolTableEntry entry : cachedMetadata.entries()) {
      this.visibleMembersSymbolTable.add(entry);
    }

    // Add all of it's sub-traits
    for (SymbolTableEntry baseTrait : cachedMetadata.baseTraits()) {
      cachedMetadata.seenBaseTraits.add(baseTrait);
    }

    // Notify caller that this class was already processed, and we
    // just added the entries.
    return true;
  }

  public QIterable<SymbolTableEntry> entryByName(String name) {
    return this.visibleMembersSymbolTable.entryByName(name);
  }

  public QIterable<SymbolTableEntry> entries() {
    return this.visibleMembersSymbolTable.entries();
  }

  public QIterable<SymbolTableEntry> baseTraits() {
    return new QList<>(this.seenBaseTraits);
  }

  public boolean containsInheritanceLoop() {
    return this.containsInheritanceLoop;
  }

  public boolean hasUnresolveableTraits() {
    return this.unresolvableTraits.size() > 0;
  }

  public Set<FullyQualifiedNameExpression> unresolvedTraits() {
    return this.unresolvableTraits;
  }

  public boolean containsKey(String name) {
    return this.visibleMembersSymbolTable.containsKey(name);
  }

  public SimpleSymbolTable immediateMembers() {
    return this.immediateMembers;
  }
}
