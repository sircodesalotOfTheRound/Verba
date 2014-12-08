package com.verba.language.parse.expressions.blockheader.functions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventSubscriptionBase;
import com.verba.language.build.event.subscriptions.FunctionEventSubscription;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.rules.FunctionDeclarationBacktrackRule;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parse.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parse.expressions.categories.*;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.expressions.members.MemberExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;
import com.verba.tools.exceptions.CompilerException;

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

    BuildEvent.ContainsEventSubscriptionObject
{

  private final FunctionEventSubscription eventSubscription = new FunctionEventSubscription(this);
  private final FullyQualifiedNameExpression identifier;
  private final BlockDeclarationExpression block;
  private final boolean isConstructor;
  private TypeConstraintExpression explicitReturnType;

  public FunctionDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.isConstructor = determineIsConstructorFunction(parent, lexer);
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);

    if (lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
      this.explicitReturnType = TypeConstraintExpression.read(this, lexer);
    }

    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  private boolean determineIsConstructorFunction(VerbaExpression parent, Lexer lexer) {
    if (lexer.currentIs(KeywordToken.class, KeywordToken.FN)) {
      lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.FN);
      return false;
    } else {
      this.enforceIsConstructorMethod(parent, lexer);
      return true;
    }
  }

  private void enforceIsConstructorMethod(VerbaExpression parent, Lexer lexer) {
    if (!FunctionDeclarationBacktrackRule.isConstructorSignature(parent, lexer)) {
      throw new CompilerException("Function declarations must be constructors or start with 'fn'");
    }
  }

  public static FunctionDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new FunctionDeclarationExpression(parent, lexer);
  }

  // Accessors
  public boolean isConstructor() { return this.isConstructor; }

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
  public TypeConstraintExpression typeConstraint() {
    return this.explicitReturnType;
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(Scope symbolTable) {
    symbolTable.visit(this);
  }

  public Symbol resolvedType() { return this.eventSubscription.returnType(); }

  @Override
  public BuildEventSubscriptionBase buildEventObject() { return eventSubscription; }
}
