package com.verba.language.emit.opcodes;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class LdStrOpCode implements VerbajOpCode {
  private final VirtualVariable variable;
  private final String text;

  public LdStrOpCode(VirtualVariable variable, String text) {
    this.variable = variable;
    this.text = text;
  }

  @Override
  public int opNumber() { return 0xd1; }

  @Override
  public String opName() { return "LdStr"; }

  @Override
  public void render(FunctionOpCodeRenderer renderer) {
    renderer.writeInt8("varnum", variable.variableNumber());
    renderer.writeString("text", text);
  }

  public String text() { return this.text; }
  public VirtualVariable variable() { return this.variable; }
}
