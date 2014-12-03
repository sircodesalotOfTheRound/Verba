package com.verba.language.parsing.expressions.rvalue.simple;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.LiteralExpression;
import com.verba.language.parsing.expressions.categories.MathOperandExpression;
import com.verba.language.parsing.expressions.categories.NativeTypeExpression;
import com.verba.language.parsing.expressions.categories.TypeDeclarationExpression;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.literals.QuoteToken;
import com.verba.virtualmachine.VirtualMachineNativeTypes;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class QuoteExpression extends VerbaExpression
  implements LiteralExpression, NativeTypeExpression, MathOperandExpression {

  private final LexInfo token;

  public QuoteExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);
    this.token = lexer.readCurrentAndAdvance(QuoteToken.class);
    this.closeLexingRegion();
  }

  public static QuoteExpression read(VerbaExpression parent, Lexer lexer) {
    return new QuoteExpression(parent, lexer);
  }

  public String representation() {
    return token.representation();
  }

  public String innerText() {
    String rep = representation();
    return rep.substring(1, rep.length() - 1);
  }

  public LexInfo quotation() {
    return this.token;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public TypeDeclarationExpression nativeTypeDeclaration() {
    return VirtualMachineNativeTypes.UTF8;
  }

}
