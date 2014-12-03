package com.verba.language.parse.expressions.blockheader.classes;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.BuildAnalysis;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.subscriptions.PolymorphicExpressionBuildEventHandler;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parse.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parse.expressions.categories.GenericallyParameterizedExpression;
import com.verba.language.parse.expressions.categories.ParameterizedExpression;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.expressions.members.MemberExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.mathop.OperatorToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class PolymorphicDeclarationExpression extends VerbaExpression
  implements NamedBlockExpression,
  ParameterizedExpression,
  GenericallyParameterizedExpression,
  SymbolTableExpression,
  BuildEvent.NotifySymbolTableBuildEvent
{
  private final PolymorphicExpressionBuildEventHandler buildProfile = new PolymorphicExpressionBuildEventHandler(this);
  private final FullyQualifiedNameExpression identifier;
  private BlockDeclarationExpression block;

  private QIterable<TypeDeclarationExpression> traits;

  private PolymorphicDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    if (lexer.currentIs(KeywordToken.class, "class") || lexer.currentIs(KeywordToken.class, "trait")) {
      lexer.readCurrentAndAdvance(KeywordToken.class, "class");
    }

    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    this.traits = readBaseTypes(lexer);
    this.block = BlockDeclarationExpression.read(this, lexer);

    this.closeLexingRegion();
  }

  private QIterable<TypeDeclarationExpression> readBaseTypes(Lexer lexer) {
    QList<TypeDeclarationExpression> baseTypes = new QList<>();

    if (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
    } else {
      return baseTypes;
    }

    do {
      baseTypes.add(TypeDeclarationExpression.read(this, lexer));

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

  // Build Events
  @Override
  public void beforeSymbolTableAssociation(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) {
    this.buildProfile.beforeSymbolTableAssociation(analysis, buildAnalysis);
  }

  @Override
  public void afterSymbolTableAssociation(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {
    this.buildProfile.afterSymbolTableAssociation(buildAnalysis, staticSpace, symbolTable);
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

  public QIterable<SymbolTableEntry> traitSymbolTableEntries() { return this.buildProfile.traitEntries(); }

  public QIterable<TypeDeclarationExpression> traits() {
    return this.traits;
  }

  public QIterable<SymbolTableEntry> allMembers() { return this.buildProfile.allMembers(); }

  public QIterable<SymbolTableEntry> immediateMembers() { return this.buildProfile.immediateMembers(); }

  public boolean isDerivedFrom(String name) { return buildProfile.isDerivedFrom(name); }

  public boolean isMember(String name) {
    return false;
  }

  public boolean isImmediateMember(String name) {
    return buildProfile.isImmediateMember(name);
  }

  public QIterable<SymbolTableEntry> findMembersByName(String name) {
    return buildProfile.findMembersByName(name);
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
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {
    symbolTable.visit(this);
  }

  @Override
  public boolean hasParameters() { return this.primaryIdentifier().hasParameters(); }

  @Override
  public QIterable<TupleDeclarationExpression> parameterSets() { return this.primaryIdentifier().parameterLists(); }


}
