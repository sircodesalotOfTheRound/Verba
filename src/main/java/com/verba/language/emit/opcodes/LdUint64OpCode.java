package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class LdUint64OpCode extends VerbatimOpCodeBase {
  private static final VerbatimOpCodeBinaryValue LD_UI64 = VerbatimOpCodeBinaryValue.LD_UI64;

  private final VirtualVariable variable;
  private final long value;


  public LdUint64OpCode(VirtualVariable variable, long value) {
    super(LD_UI64);

    this.variable = variable;
    this.value = value;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("varnum", variable.variableNumber());
    renderer.writeInt64("value", value);
  }

  public long value() { return value; }
  public VirtualVariable variable() { return this.variable; }
}
