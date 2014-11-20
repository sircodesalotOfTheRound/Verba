package com.verba.language.graph.symbols.table.tables;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.graph.validation.violations.ValidationViolation;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parsing.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.classes.TraitDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.TaskDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parsing.expressions.blockheader.namespaces.NamespaceDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.categories.NamedExpression;
import com.verba.language.parsing.expressions.categories.SymbolTableExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.graph.symbols.meta.GenericParameterSymbolTableItem;
import com.verba.language.graph.symbols.meta.NestedSymbolTableMetadata;
import com.verba.language.graph.symbols.meta.ParameterSymbolTableItem;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntrySet;

import java.io.Serializable;


/**
 * Created by sircodesalot on 14-3-9.
 */
public class ScopedSymbolTable implements Serializable {
  private final String fqn;
  private final String name;
  private final ScopedSymbolTable parent;
  private final SymbolTableExpression headerExpression; // The block item (function, class, anonymous-block, ...)
  private final SymbolTableEntrySet entrySet;
  private final QList<ValidationViolation> violations = new QList<>();
  private final QList<ScopedSymbolTable> nestedTables = new QList<>();

  // Construction
  // Anonymous block
  public ScopedSymbolTable(SymbolTableExpression block) {
    this(null, block);
  }

  public ScopedSymbolTable(ScopedSymbolTable parent, SymbolTableExpression block) {
    this.entrySet = new SymbolTableEntrySet(this);
    this.name = resolveName(block);
    this.parent = parent;
    this.headerExpression = block;
    this.fqn = resolveFqn(name);

    block.accept(this);
  }

  private String resolveName(SymbolTableExpression block) {
    if (block instanceof NamedExpression) {
      return ((NamedExpression) block).name();
    }

    return null;
  }

  private String resolveFqn(String name) {
    if (this.hasParentTable() && (this.parent.fqn != null) && (name != null)) {
      return String.format("%s.%s", this.parent.fqn(), name);
    }

    return name;
  }



  public void visit(StaticSpaceExpression block) {
    for (SymbolTableExpression expression : block.rootLevelExpressions().ofType(SymbolTableExpression.class)) {
      if (expression instanceof NamedBlockExpression) {
        this.addNested(((NamedBlockExpression) expression).name(), expression);
      } else {
        expression.accept(this);
      }
    }
  }

  public void visit(TaskDeclarationExpression expression) {
//    this.addNested(expression);
  }

  public void visit(VerbaCodePage block) {
    // This is technically incorrect. See 'visit(StaticSpaceExpression)'
    for (NamedExpression expression : block.expressions().ofType(NamedExpression.class)) {
      this.addNested(expression.name(), (SymbolTableExpression)expression);
    }
  }

  public void visit(ClassDeclarationExpression classDeclaration) {
    this.visit(classDeclaration.genericParameters());
    this.visit(classDeclaration.block());
  }

  public void visit(TraitDeclarationExpression trait) {
    this.visit(trait.genericParameters());
    this.visit(trait.block());
  }

  public void visit(NamespaceDeclarationExpression namespace) {
    this.addNested(namespace.name(), namespace);
  }

  public void visit(FunctionDeclarationExpression function) {
    // First add the parameterSets
    QIterable<NamedValueExpression> parameters = function.parameterSets()
      .flatten(TupleDeclarationExpression::items)
      .cast(NamedValueExpression.class);

    this.visit(function.genericParameters());

    // Then add regular parameterSets
    for (NamedValueExpression parameter : parameters) {
      this.add(parameter.identifier().representation(), parameter, new ParameterSymbolTableItem());
    }

    // Add symbols. In particular add sub-tables for named block expressions.
    for (SymbolTableExpression subExpression : function.block().ofType(SymbolTableExpression.class)) {
      if (subExpression instanceof NamedBlockExpression) {
        NamedBlockExpression block = (NamedBlockExpression) subExpression;
        this.addNested(block.name(), block);

      } else {
        subExpression.accept(this);
      }
    }
  }

  public void visit(SignatureDeclarationExpression signature) {
    this.add(signature.name(), signature);
  }

  public void visit(GenericTypeListExpression genericParameters) {
    // Add generic parameterSets
    for (NamedValueExpression genericParam : genericParameters) {
      this.add(genericParam.identifier().representation(), genericParam, new GenericParameterSymbolTableItem());
    }
  }

  public void visit(BlockDeclarationExpression block) {
    for (SymbolTableExpression expression : block.ofType(SymbolTableExpression.class)) {
      expression.accept(this);
    }
  }

  // Additions
  public void add(SymbolTableEntry entry) {
    this.entrySet.add(entry);
  }

  public void add(String name, VerbaExpression object, SymbolTableMetadata... metadata) {
    // Add a new symbol table entry, mark it as a nested symbol table item.
    SymbolTableEntry entry = new SymbolTableEntry(name, this, object);

    // Add Metadata
    for (SymbolTableMetadata metadataItem : metadata) {
      entry.addMetadata(metadataItem);
    }

    // Add the entry to the symbol table
    this.add(entry);
  }

  public void addNested(String name, SymbolTableExpression block) {
    ScopedSymbolTable nestedTable = new ScopedSymbolTable(this, block);

    this.add(name, (VerbaExpression) block, new NestedSymbolTableMetadata(nestedTable));
    this.nestedTables.add(nestedTable);
  }

  public void visit(ValDeclarationStatement statement) {
    this.add(statement.name(), statement);
  }

  // Accessing Items
  public String name() {
    return this.name;
  }

  public boolean hasName() {
    return this.name != null;
  }

  public boolean hasParentTable() {
    return this.parent != null;
  }

  public ScopedSymbolTable parent() {
    return this.parent;
  }

  public boolean hasItems() {
    return this.entrySet.count() > 0;
  }

  public void clear() {
    this.entrySet.clear();
  }

  public void addViolation(ValidationViolation violation) {
    this.violations.add(violation);
  }

  public QIterable<SymbolTableEntry> get(String key) {
    return this.entrySet.get(key);
  }

  public SymbolTableEntry get(int index) {
    return this.entrySet.get(index);
  }

  public QIterable<String> keys() {
    return new QList<>(this.entrySet.keySet());
  }

  public boolean containsKey(String key) {
    return this.entrySet.containsKey(key);
  }

  public QIterable<SymbolTableEntry> entries() {
    return this.entrySet.entries();
  }

  public int getIndex(SymbolTableEntry entry) {
    return this.entrySet.getIndex(entry);
  }

  public QIterable<ScopedSymbolTable> nestedTables() {
    return this.nestedTables;
  }

  public String fqn() {
    return this.fqn;
  }

  public SymbolTableExpression header() {
    return this.headerExpression;
  }

  public QIterable<ValidationViolation> violations() {
    return collectViolations(new QList<>(), this);
  }

  private QList<ValidationViolation> collectViolations(QList<ValidationViolation> violations,
                                                       ScopedSymbolTable onTable) {
    // Tree descent
    for (ScopedSymbolTable table : onTable.nestedTables) {
      collectViolations(violations, table);
    }

    // Violation capturing
    for (ValidationViolation violation : onTable.violations) {
      violations.add(violation);
    }

    return violations;
  }

  public QIterable<SymbolTableEntry> resolveName(String name) {
    QList<SymbolTableEntry> resolvedNames = new QList<>();
    ScopedSymbolTable searchTable = this;

    // Search upward through all parent scopes to find items
    // that match the name.
    do {
      if (searchTable.containsKey(name)) {
        resolvedNames.add(searchTable.get(name));
      }

      searchTable = searchTable.parent();
    } while (searchTable != null);

    return resolvedNames;
  }

}
