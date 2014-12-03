package com.verba.language.parse.expressions.blockheader.classes;

import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parse.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class MetaDeclarationExpression extends VerbaExpression implements NamedBlockExpression {
  private final FullyQualifiedNameExpression identifier;
  private final TupleDeclarationExpression parameterList;
  private final BlockDeclarationExpression block;
  private final GenericTypeListExpression genericTypeList;

  public MetaDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "meta");

    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    this.genericTypeList = GenericTypeListExpression.read(this, lexer);
    this.parameterList = TupleDeclarationExpression.read(this, lexer);
    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static MetaDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new MetaDeclarationExpression(parent, lexer);
  }

  public FullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  public TupleDeclarationExpression parameterList() {
    return this.parameterList;
  }

  public BlockDeclarationExpression block() {
    return this.block;
  }

  public GenericTypeListExpression genericParameters() {
    return this.genericTypeList;
  }

  @Override
  public String name() {
    return this.identifier.members().first().memberName();
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    throw new NotImplementedException();
  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {

  }
}
