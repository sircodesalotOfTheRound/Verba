package com.verba.language.parse.expressions.blockheader.classes;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventSubscriptionBase;
import com.verba.language.build.event.subscriptions.PolymorphicBuildEventHandler;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parse.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parse.expressions.categories.GenericallyParameterizedExpression;
import com.verba.language.parse.expressions.categories.ParameterizedExpression;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
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
public class PolymorphicDeclarationExpression extends VerbaExpression
  implements NamedBlockExpression,
  ParameterizedExpression,
  GenericallyParameterizedExpression,
  SymbolTableExpression,
  BuildEvent.ContainsEventSubscriptionObject
{
  private final PolymorphicBuildEventHandler eventSubscription = new PolymorphicBuildEventHandler(this);
  private final FullyQualifiedNameExpression identifier;
  private final boolean isClass;
  private BlockDeclarationExpression block;

  private QIterable<TypeConstraintExpression> traits;

  private PolymorphicDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.isClass = determineIsClass(lexer);
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    this.traits = readBaseTypes(lexer);
    this.block = BlockDeclarationExpression.read(this, lexer);

    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  private boolean determineIsClass(Lexer lexer) {
    if (lexer.currentIs(KeywordToken.class, KeywordToken.CLASS)) {
      lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.CLASS);
      return true;
    } else if (lexer.currentIs(KeywordToken.class, KeywordToken.TRAIT)) {
      lexer.readCurrentAndAdvance(KeywordToken.class, KeywordToken.TRAIT);
      return false;
    }

    throw new CompilerException("Polymorphic Expressions must be classes or traits");
  }

  private QIterable<TypeConstraintExpression> readBaseTypes(Lexer lexer) {
    QList<TypeConstraintExpression> baseTypes = new QList<>();

    if (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
    } else {
      return baseTypes;
    }

    do {
      baseTypes.add(TypeConstraintExpression.read(this, lexer));

      // Read a comma if that's the next item, else break.
      if (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ",")) {
        lexer.readCurrentAndAdvance(OperatorToken.class, ",");
      }
      else {
        break;
      }

    } while (lexer.notEOF());

    return baseTypes;
  }

  public static PolymorphicDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new PolymorphicDeclarationExpression(parent, lexer);
  }

  // Accessors
  public FullyQualifiedNameExpression declaration() {
    return this.identifier;
  }

  public MemberExpression primaryIdentifier() {
    return this.declaration().first();
  }

  public QIterable<TupleDeclarationExpression> inlineParameters() {
    return this.primaryIdentifier().parameterLists();
  }

  public GenericTypeListExpression genericParameters() {
    return this.primaryIdentifier().genericParameterList();
  }

  public QIterable<Symbol> traitSymbolTableEntries() { return this.eventSubscription.traitEntries(); }

  public QIterable<TypeConstraintExpression> traits() {
    return this.traits;
  }

  public QIterable<Symbol> allMembers() { return this.eventSubscription.allMembers(); }

  // Membership
  public QIterable<Symbol> immediateMembers() { return this.eventSubscription.immediateMembers(); }

  public boolean isClass() { return this.isClass; }

  public boolean isDerivedFrom(String name) { return eventSubscription.isDerivedFrom(name); }

  public boolean isMember(String name) {
    return false;
  }

  public boolean isImmediateMember(String name) {
    return eventSubscription.isImmediateMember(name);
  }

  public QIterable<Symbol> findMembersByName(String name) {
    return eventSubscription.findMembersByName(name);
  }

  @Override
  public BlockDeclarationExpression block() {
    return this.block;
  }

  public boolean isInlineClass() {
    return (this.primaryIdentifier().hasParameters() || !this.hasBlock());
  }

  @Override
  public boolean hasGenericParameters() {
    return this.primaryIdentifier().hasGenericParameters();
  }

  public boolean hasTraits() {
    return (this.traits != null);
  }

  public boolean hasBlock() {
    return (this.block != null && this.block.hasItems());
  }

  @Override
  public String name() {
    return this.identifier.members().first().memberName();
  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(Scope symbolTable) {
    symbolTable.visit(this);
  }

  @Override
  public boolean hasParameters() { return this.primaryIdentifier().hasParameters(); }

  @Override
  public QIterable<TupleDeclarationExpression> parameterSets() { return this.primaryIdentifier().parameterLists(); }


  @Override
  public BuildEventSubscriptionBase buildEventObject() { return eventSubscription; }
}
