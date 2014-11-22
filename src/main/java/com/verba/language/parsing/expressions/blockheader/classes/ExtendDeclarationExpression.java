package com.verba.language.parsing.expressions.blockheader.classes;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.NamedBlockExpression;
import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class ExtendDeclarationExpression extends VerbaExpression implements NamedBlockExpression {
  private final FullyQualifiedNameExpression identifier;
  private final BlockDeclarationExpression block;
  private final GenericTypeListExpression genericTypeList;

  public ExtendDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "extend");
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);
    this.genericTypeList = GenericTypeListExpression.read(this, lexer);

    this.block = BlockDeclarationExpression.read(this, lexer);
    this.closeLexingRegion();
  }

  public static ExtendDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new ExtendDeclarationExpression(parent, lexer);
  }

  @Override
  public QIterable<ExpressionValidator> validators() {
    return null;
  }

  private String getTypeListString() {
    if (this.genericTypeList == null) return "";
    else return this.genericTypeList.toString();
  }

  public FullyQualifiedNameExpression identifer() {
    return this.identifier;
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

  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {

  }
}
