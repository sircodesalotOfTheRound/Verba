package com.verba.language.parsing.expressions.blockheader.functions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.graph.analysis.expressions.profiles.FunctionExpressionEventSubscription;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parsing.expressions.categories.*;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.expressions.members.MemberExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class FunctionDeclarationExpression extends VerbaExpression
  implements NamedBlockExpression,
    TypedExpression,
    ParameterizedExpression,
    GenericallyParameterizedExpression,
    SymbolTableExpression,
    NamedAndTypedExpression,

    BuildEvent.NotifySymbolTableBuildEvent,
    BuildEvent.NotifyObjectEmitEvent
{

  private final FunctionExpressionEventSubscription buildEvents = new FunctionExpressionEventSubscription(this);
  private final FullyQualifiedNameExpression identifier;
  private final BlockDeclarationExpression block;
  private TypeDeclarationExpression explicitReturnType;

  public FunctionDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);
    lexer.readCurrentAndAdvance(KeywordToken.class, "fn");
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);

    if (lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
      this.explicitReturnType = TypeDeclarationExpression.read(this, lexer);
    }

    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static FunctionDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new FunctionDeclarationExpression(parent, lexer);
  }


  // Events
  @Override
  public ObjectImage onGenerateObjectImage(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {
    return this.buildEvents.onGenerateObjectImage(buildAnalysis, staticSpace, symbolTable);
  }

  @Override
  public void beforeSymbolTableAssociation(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) {
    buildEvents.beforeSymbolTableAssociation(analysis, buildAnalysis);
  }

  @Override
  public void afterSymbolTableAssociation(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {
    buildEvents.afterSymbolTableAssociation(buildAnalysis, staticSpace, symbolTable);
  }

  // Accessors
  public boolean hasGenericParameters() {
    return this.primaryIdentifier().hasGenericParameters();
  }

  @Override
  public boolean hasParameters() {
    return (this.primaryIdentifier().hasParameters());
  }

  public MemberExpression primaryIdentifier() {
    return this.declaration().first();
  }

  public FullyQualifiedNameExpression declaration() {
    return this.identifier;
  }

  @Override
  public BlockDeclarationExpression block() {
    return this.block;
  }

  @Override
  public QIterable<TupleDeclarationExpression> parameterSets() {
    return this.primaryIdentifier().parameterLists();
  }

  public GenericTypeListExpression genericParameters() {
    return this.primaryIdentifier().genericParameterList();
  }

  @Override
  public String name() {
    return this.primaryIdentifier().memberName();
  }

  @Override
  public boolean hasTypeConstraint() {
    return this.explicitReturnType != null;
  }

  @Override
  public TypeDeclarationExpression typeDeclaration() {
    return this.explicitReturnType;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {
    symbolTable.visit(this);
  }

  public SymbolTableEntry returnType() {
    return this.buildEvents.returnType();
  }
}
