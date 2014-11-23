package com.verba.language.parsing.expressions.blockheader.classes;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.analysis.expressions.tools.ExpressionAnalysisBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parsing.expressions.categories.GenericallyParameterizedExpression;
import com.verba.language.parsing.expressions.categories.PolymorphicExpression;
import com.verba.language.parsing.expressions.categories.SymbolTableExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.expressions.members.MemberExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class TraitDeclarationExpression extends VerbaExpression
  implements NamedBlockExpression, PolymorphicExpression, GenericallyParameterizedExpression, SymbolTableExpression {

  private final FullyQualifiedNameExpression identifier;
  private final BlockDeclarationExpression block;
  private QIterable<TypeDeclarationExpression> traits;

  public TraitDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "trait");
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);

    if (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ":");

      this.traits = readBaseTypes(lexer);
    }

    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  private QIterable<TypeDeclarationExpression> readBaseTypes(Lexer lexer) {
    QList<TypeDeclarationExpression> baseTypes = new QList<>();

    do {
      baseTypes.add(TypeDeclarationExpression.read(this, lexer));

      // Read a comma if that's the next item, else break.
      if (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ","))
        lexer.readCurrentAndAdvance(OperatorToken.class, ",");
      else
        break;

    } while (lexer.notEOF());

    return baseTypes;
  }

  public static TraitDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new TraitDeclarationExpression(parent, lexer);
  }

  @Override
  public ExpressionAnalysisBase expressionAnalysis() {
    return null;
  }

  public BlockDeclarationExpression block() {
    return this.block;
  }

  @Override
  public QIterable<TypeDeclarationExpression> traits() {
    return this.traits;
  }

  public FullyQualifiedNameExpression declaration() {
    return this.identifier;
  }

  public MemberExpression primaryIdentifier() {
    return this.declaration().first();
  }

  public QIterable<TupleDeclarationExpression> inlineParameters() {
    return this.primaryIdentifier().parameterLists();
  }

  @Override
  public boolean hasGenericParameters() {
    return this.primaryIdentifier().hasGenericParameters();
  }

  @Override
  public GenericTypeListExpression genericParameters() {
    return this.primaryIdentifier().genericParameterList();
  }

  @Override
  public String name() {
    return this.identifier.members().first().memberName();
  }

  public boolean isInlineTrait() {
    return (this.primaryIdentifier().hasParameters() || !this.hasBlock());
  }

  public boolean hasBlock() {
    return (this.block != null && this.block.hasItems());
  }

  @Override
  public boolean hasTraits() {
    return this.traits != null;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {
    symbolTable.visit(this);
  }
}
