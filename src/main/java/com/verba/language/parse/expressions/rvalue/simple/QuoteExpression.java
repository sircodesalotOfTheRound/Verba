package com.verba.language.parse.expressions.rvalue.simple;

import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.*;
import com.verba.language.parse.info.LexInfo;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.literals.QuoteToken;
import com.verba.virtualmachine.VirtualMachineNativeTypes;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class QuoteExpression extends VerbaExpression
  implements LiteralExpression, NativeTypeExpression, MathOperandExpression
{

  private final LexInfo token;

  public QuoteExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);
    this.token = lexer.readCurrentAndAdvance(QuoteToken.class);
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

  }

  @Override
  public void onValidate(Build build, SymbolTable table) {

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
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public TypeConstraintExpression nativeTypeDeclaration() {
    return VirtualMachineNativeTypes.UTF8;
  }

  @Override
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return null;
  }
}
