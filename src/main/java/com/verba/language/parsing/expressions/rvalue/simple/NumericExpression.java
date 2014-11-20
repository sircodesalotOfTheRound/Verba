package com.verba.language.parsing.expressions.rvalue.simple;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.categories.*;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.tokens.NumericToken;
import com.verba.virtualmachine.VirtualMachineNativeTypes;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class NumericExpression extends VerbaExpression
  implements LiteralExpression, TupleItemExpression, MarkupRvalueExpression, NativeTypeExpression,
  MathOperandExpression {

  private LexInfo token;

  public enum Size {
    BYTE,
    SHORT,
    INTEGER,
    LONG;

    public static Size determineSize(LexInfo token) {
      long value = Long.parseLong(token.representation());
      if ((value >= Byte.MIN_VALUE) && (value <= Byte.MAX_VALUE)) return Size.BYTE;
      else if ((value >= Short.MIN_VALUE) && (value <= Short.MAX_VALUE)) return Size.SHORT;
      else if ((value >= Integer.MIN_VALUE) && (value <= Integer.MAX_VALUE)) return Size.INTEGER;
      else if ((value >= Long.MIN_VALUE) && (value <= Long.MAX_VALUE)) return Size.LONG;

      else return Size.LONG;
    }
  }

  private NumericExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.token = lexer.readCurrentAndAdvance(NumericToken.class);
    this.closeLexingRegion();
  }

  public static NumericExpression read(VerbaExpression parent, Lexer lexer) {
    return new NumericExpression(parent, lexer);
  }

  @Override
  public TypeDeclarationExpression nativeTypeDeclaration() {
    return VirtualMachineNativeTypes.INT32_LITERAL;
  }

  public boolean isPositive() {
    return (this.asLong() > 0);
  }

  public boolean isDecimal() {
    return this.number().representation().contains(".");
  }

  public LexInfo number() {
    return token;
  }

  public Size size() {
    return Size.determineSize(token);
  }

  public long asLong() {
    return Long.parseLong(this.token.representation());
  }

  public int asInt() {
    return Integer.parseInt(this.token.representation());
  }

  public double asDecimal() {
    return Double.parseDouble(this.token.representation());
  }


  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }
}


