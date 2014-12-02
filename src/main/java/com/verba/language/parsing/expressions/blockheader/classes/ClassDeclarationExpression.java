package com.verba.language.parsing.expressions.blockheader.classes;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.analysis.expressions.profiles.PolymorphicExpressionBuildProfile;
import com.verba.language.graph.analysis.expressions.tools.BuildProfileBase;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
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
public class ClassDeclarationExpression extends VerbaExpression
  implements NamedBlockExpression, PolymorphicExpression, ParameterizedExpression,
  GenericallyParameterizedExpression, SymbolTableExpression {

  private final PolymorphicExpressionBuildProfile buildProfile = new PolymorphicExpressionBuildProfile(this);
  private final FullyQualifiedNameExpression identifier;
  private BlockDeclarationExpression block;

  private QIterable<TypeDeclarationExpression> traits;

  private ClassDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "class");
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

  public static ClassDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new ClassDeclarationExpression(parent, lexer);
  }

  @Override
  public BuildProfileBase buildProfile() { return buildProfile; }

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

  @Override
  public QIterable<SymbolTableEntry> traitSymbolTableEntries() { return this.buildProfile.traitEntries(); }

  @Override
  public QIterable<TypeDeclarationExpression> traits() {
    return this.traits;
  }

  @Override
  public QIterable<SymbolTableEntry> allMembers() { return this.buildProfile.allMembers(); }

  @Override
  public QIterable<SymbolTableEntry> immediateMembers() { return this.buildProfile.immediateMembers(); }

  @Override
  public boolean isMember(String name) {
    return false;
  }

  @Override
  public boolean isImmediateMember(String name) {
    return buildProfile.isImmediateMember(name);
  }

  @Override
  public QIterable<SymbolTableExpression> findMembersByName(String name) {
    return null;
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

  @Override
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
