package com.verba.language.parse.expressions.blockheader.classes;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.resolution.PolymorphicDeclarationMemberResolver;
import com.verba.language.graph.symbols.resolution.PolymorphicDeclarationTraitResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
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
  SymbolTableExpression
{
  private final FullyQualifiedNameExpression identifier;
  private final boolean isClass;
  private BlockDeclarationExpression block;

  private QIterable<TypeConstraintExpression> traitNames;
  private final PolymorphicDeclarationMemberResolver memberResolver;
  private final PolymorphicDeclarationTraitResolver traitResolver;

  private PolymorphicDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.isClass = determineIsClass(lexer);
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    this.traitNames = readBaseTypes(lexer);
    this.block = BlockDeclarationExpression.read(this, lexer);

    this.memberResolver = new PolymorphicDeclarationMemberResolver(this);
    this.traitResolver = new PolymorphicDeclarationTraitResolver(this);

    this.closeLexingRegion();
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void afterContentsParsed(Build build) {

  }

  @Override
  public void afterSymbolsGenerated(Build build, SymbolTable table) {

  }

  @Override
  public void onResolveSymbols(Build build, SymbolTable table) {
    this.memberResolver.resolve(table);
    this.traitResolver.resolve(table);
  }

  @Override
  public void onValidate(Build build, SymbolTable table) {

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

  public QIterable<Symbol> traits() { return this.traitResolver.traits(); }

  public QIterable<TypeConstraintExpression> traitNames() {
    return this.traitNames;
  }

  public QIterable<Symbol> allMembers() { return null; }// this.eventSubscription.allMembers(); }

  // Membership
  public QIterable<Symbol> immediateMembers() {
    return this.memberResolver.immediateMembers();
  }

  public boolean isClass() { return this.isClass; }

  public boolean isDerivedFrom(String name) { return false; } //eventSubscription.isDerivedFrom(name); }

  public boolean isMember(String name) {
    return false;
  }

  public boolean isImmediateMember(String name) {
    return this.memberResolver.containsImmediateMemberWithName(name);
  }

  public QIterable<Symbol> findImmediateMembersByName(String name) {
    return this.memberResolver.findImmediateMembersByName(name);
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
    return (this.traitNames != null);
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
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return visitor.visit(this);
  }
}
