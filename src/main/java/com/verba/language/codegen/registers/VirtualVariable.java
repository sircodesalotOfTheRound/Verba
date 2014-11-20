package com.verba.language.codegen.registers;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class VirtualVariable {
  private final int variableNumber;
  private final TypeDeclarationExpression type;
  private final VerbaExpression expression;
  private final String text;

  public VirtualVariable(VerbaExpression expression, int variableNumber, TypeDeclarationExpression type) {
    this.expression = expression;
    this.variableNumber = variableNumber;
    this.type = type;
    this.text = expression.text();
  }

  public int variableNumber() { return this.variableNumber; }
  public TypeDeclarationExpression type() { return this.type; }
  public VerbaExpression expression() { return expression; }
  public String text() { return text; }

  @Override
  public int hashCode() {
    return (this.text + this.type).hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof VirtualVariable)) {
      return false;
    }

    VirtualVariable rhs = (VirtualVariable)obj;

    return this.variableNumber == rhs.variableNumber
      && this.type.representation().equals(rhs.type.representation())
      && this.text.equals(rhs.text);
  }
}
