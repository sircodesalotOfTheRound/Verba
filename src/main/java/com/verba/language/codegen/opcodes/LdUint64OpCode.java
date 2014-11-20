package com.verba.language.codegen.opcodes;

import com.verba.language.codegen.registers.VirtualVariable;
import com.verba.language.codegen.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class LdUint64OpCode implements VerbajOpCode {
  private final VirtualVariable variable;
  private final long value;


  public LdUint64OpCode(VirtualVariable variable, long value) {
    this.variable = variable;
    this.value = value;
  }

  @Override
  public int opNumber() { return 0xd3; }

  @Override
  public String opName() { return "LdUi64"; }

  @Override
  public void render(FunctionOpCodeRenderer renderer) {
    renderer.writeInt8("variableNumber", variable.variableNumber());
    renderer.writeInt64("value", value);
  }

  public long value() { return value; }
  public VirtualVariable variable() { return this.variable; }
}
