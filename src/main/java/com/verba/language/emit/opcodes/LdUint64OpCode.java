package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class LdUint64OpCode extends VerbajOpCodeBase {
  private static final int opNumber = 0xD3;
  private static final String opName = "LdUi64";

  private final VirtualVariable variable;
  private final long value;


  public LdUint64OpCode(VirtualVariable variable, long value) {
    super(opNumber, opName);

    this.variable = variable;
    this.value = value;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("variableNumber", variable.variableNumber());
    renderer.writeInt64("value", value);
  }

  public long value() { return value; }
  public VirtualVariable variable() { return this.variable; }
}
