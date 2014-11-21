package com.verba.language.parsing.expressions;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.graph.tools.SyntaxTreeFlattener;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.parsing.expressions.categories.SymbolTableExpression;
import com.verba.language.graph.symbols.meta.interfaces.SymbolTypeMetadata;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-5-14.
 */
public class StaticSpaceExpression extends VerbaExpression implements SymbolTableExpression {
  private GlobalSymbolTable symbolTable;
  private SyntaxTreeFlattener allSubExpressions;
  private final QList<VerbaExpression> rootExpressions = new QList<>();

  public StaticSpaceExpression(Iterable<VerbaExpression> rootExpressions) {
    super(null, null);

    for (VerbaExpression expression : rootExpressions)
      this.rootExpressions.add(expression);

    this.update();
  }

  public StaticSpaceExpression(VerbaExpression... rootExpressions) {
    super(null, null);

    for (VerbaExpression expression : rootExpressions)
      this.rootExpressions.add(expression);

    this.update();
  }

  /*
  public StaticSpaceExpression(SourceFilePathInfo path) {
    this(VerbaCodePage.fromFile(null, path.absolutePath()));
  }*/

  public QIterable<VerbaExpression> rootLevelExpressions() {
    return this.rootExpressions;
  }

  public GlobalSymbolTable globalSymbolTable() {
    return this.symbolTable;
  }

  public SyntaxTreeFlattener allSubExpressions() {
    return this.allSubExpressions;
  }

  public void update() {
    this.symbolTable = new GlobalSymbolTable(this);
    this.allSubExpressions = new SyntaxTreeFlattener(this);
  }

  public void resolveSymbolNames() {
    this.globalSymbolTable().resolveSymbolNames();
  }

  public SymbolTableEntry entryByInstance(VerbaExpression expression) {
    return symbolTable.getByInstance(expression);
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {
    symbolTable.visit(this);
  }

  public GlobalSymbolTable symbolTable() { return this.symbolTable; }

  public TypeDeclarationExpression getObjectType(ValDeclarationStatement statement) {
    return entryByInstance(statement)
      .metadata()
      .ofType(SymbolTypeMetadata.class)
      .single()
      .symbolType();
  }
}
