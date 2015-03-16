package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class LdUnitOpCode extends VerbatimOpCodeBase {
  private static final VerbatimOpCodeBinaryValue LD_UNIT = VerbatimOpCodeBinaryValue.LD_UNIT;
  private final VirtualVariable variable;

  public LdUnitOpCode(VirtualVariable variable) {
    super(LD_UNIT);

    this.variable = variable;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("variable", variable.variableNumber());
  }
}
