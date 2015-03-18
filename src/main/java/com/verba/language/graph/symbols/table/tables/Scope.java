package com.verba.language.graph.symbols.table.tables;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.violations.ValidationViolation;
import com.verba.language.graph.symbols.meta.GenericParameterSymbolTableItem;
import com.verba.language.graph.symbols.meta.NestedScopeMetadata;
import com.verba.language.graph.symbols.meta.ParameterSymbolMetadata;
import com.verba.language.graph.symbols.meta.types.UserDefinedTypeMetadata;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntrySet;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parse.expressions.blockheader.namespaces.NamespaceDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.categories.GenericallyParameterizedExpression;
import com.verba.language.parse.expressions.categories.NamedExpression;
import com.verba.language.parse.expressions.categories.ParameterizedExpression;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;


/**
 * Created by sircodesalot on 14-3-9.
 */
public class Scope {
  private final String fqn;
  private final String name;
  private final Scope parent;
  private final SymbolTableExpression headerExpression; // The block item (function, class, anonymous-block, ...)
  private final SymbolTableEntrySet entrySet;
  private final QList<ValidationViolation> violations = new QList<>();
  private final QList<Scope> nestedTables = new QList<>();
  private final QList<String> fqnList;

  // Construction
  // Anonymous block
  public Scope(SymbolTableExpression region) {
    this.entrySet = new SymbolTableEntrySet(this);
    this.name = resolveName(region);
    this.parent = null;
    this.headerExpression = region;
    this.fqn = resolveFqn(name);
    this.fqnList = buildfqnList(this);

    region.accept(this);
  }

  public Scope(Scope parentTable, NamedBlockExpression blockHeader) {
    this.entrySet = new SymbolTableEntrySet(this);
    this.name = resolveName(blockHeader);
    this.parent = parentTable;
    this.headerExpression = blockHeader;
    this.fqn = resolveFqn(name);
    this.fqnList = buildfqnList(this);

    if (blockHeader instanceof GenericallyParameterizedExpression) {
      this.visit(((GenericallyParameterizedExpression)blockHeader).genericParameters());
    }

    if (blockHeader instanceof ParameterizedExpression) {
      QIterable<NamedValueExpression> parameters = ((ParameterizedExpression)blockHeader).parameterSets()
        .flatten(TupleDeclarationExpression::items)
        .cast(NamedValueExpression.class);

      this.visitCallParameters(parameters);
    }

    this.visitAll(blockHeader.block().expressions());
  }

  private String resolveName(SymbolTableExpression block) {
    if (block instanceof NamedExpression) {
      return ((NamedExpression) block).name();
    }

    return null;
  }

  private String resolveFqn(String name) {
    if (this.hasParentTable() && !this.parent.fqn.isEmpty()) {
      return String.format("%s.%s", this.parent.fqn(), name);
    }

    if (name != null) {
      return name;
    } else {
      return "";
    }
  }

  public void visit(LitFileRootExpression staticSpace) {
    this.visitAll(staticSpace.pages().cast(VerbaExpression.class));
  }

  public void visit(VerbaSourceCodeFile page) {
    this.visitAll(page.childExpressions());
  }

  public void visit(PolymorphicDeclarationExpression classDeclaration) {
    this.visit(classDeclaration.block());
  }

  public void visit(NamespaceDeclarationExpression namespace) {
    this.addNestedScope(namespace.name(), namespace);
  }

  public void visit(FunctionDeclarationExpression function) {
    this.visit(function.block());
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

  public void visit(DeclarationModifierExrpression declarationModifierExrpression) {
    VerbaExpression expression = declarationModifierExrpression.modifiedExpression();

    if (expression instanceof SymbolTableExpression) {
      ((SymbolTableExpression)expression).accept(this);
    }
  }

  public void visit(BlockDeclarationExpression block) {
    NamedBlockExpression parent = (NamedBlockExpression) block.parent();
    this.addNestedScope(parent.name(), parent);
  }

  public void visit(ValDeclarationStatement statement) {
    this.add(statement.name(), statement);
  }

  public void visitCallParameters(QIterable<NamedValueExpression> parameters) {
    // Then add regular parameterSets
    for (NamedValueExpression parameter : parameters) {
      this.add(parameter.identifier().representation(), parameter, ParameterSymbolMetadata.INSTANCE);
    }
  }

  // Additions
  public void add(Symbol entry) {
    this.entrySet.add(entry);
  }

  public void add(String name, VerbaExpression object, SymbolTableMetadata... metadata) {
    // Add a new symbol table entry, mark it as a nested symbol table item.
    Symbol entry = new Symbol(name, this, object);

    // Add Metadata
    for (SymbolTableMetadata metadataItem : metadata) {
      entry.addMetadata(metadataItem);
    }

    // Add the entry to the symbol table
    this.add(entry);
  }

  public void addNestedScope(String name, NamedBlockExpression block) {
    Scope nestedTable = new Scope(this, block);

    if (block instanceof PolymorphicDeclarationExpression) {
      this.add(name, (VerbaExpression) block, new NestedScopeMetadata(nestedTable), UserDefinedTypeMetadata.INSTANCE);
    } else {
      this.add(name, (VerbaExpression) block, new NestedScopeMetadata(nestedTable));
    }

    this.nestedTables.add(nestedTable);
  }

  private QList<String> buildfqnList(Scope scope) {
    QList<String> fqnList = new QList<>();
    Scope currentScope = scope;

    while (currentScope != null) {
      if (currentScope.fqn != null) {
        fqnList.add(fqn);
      }

      currentScope = currentScope.parent;
    }

    fqnList.add("");
    return fqnList;
  }

  // Accessing Items
  public String name() {
    return this.name;
  }

  public QIterable<String> fqnList() { return this.fqnList; }

  public boolean hasName() {
    return this.name != null;
  }

  public boolean hasParentTable() {
    return this.parent != null;
  }

  public Scope parent() {
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

  public QIterable<Symbol> get(String key) {
    return this.entrySet.get(key);
  }

  public Symbol get(int index) {
    return this.entrySet.get(index);
  }

  public QIterable<String> keys() {
    return new QList<>(this.entrySet.keySet());
  }

  public boolean isInScope(String key) {
    return this.entrySet.containsKey(key);
  }

  public QIterable<Symbol> entries() {
    return this.entrySet.entries();
  }

  public int getIndex(Symbol entry) {
    return this.entrySet.getIndex(entry);
  }

  public QIterable<Scope> nestedTables() {
    return this.nestedTables;
  }

  public String fqn() { return this.fqn; }

  public SymbolTableExpression header() {
    return this.headerExpression;
  }

  private void visitAll(QIterable<VerbaExpression> expressions) {
    for (SymbolTableExpression expression : expressions.ofType(SymbolTableExpression.class)) {
      expression.accept(this);
    }
  }

}
