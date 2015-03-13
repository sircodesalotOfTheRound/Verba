package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 *
 */
public class LdBoolOpCode extends VerbatimOpCodeBase {
  private static final VerbatimOpCodeBinaryValue LD_TRUE = VerbatimOpCodeBinaryValue.LD_TRUE;
  private static final VerbatimOpCodeBinaryValue LD_FALSE = VerbatimOpCodeBinaryValue.LD_FALSE;
  private final VirtualVariable variable;

  public LdBoolOpCode(VirtualVariable variable, boolean value) {
    super(determineOpCode(value));
    this.variable = variable;
  }

  private static VerbatimOpCodeBinaryValue determineOpCode(boolean value) {
    if (value) {
      return LD_TRUE;
    } else {
      return LD_FALSE;
    }
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("variable", variable.variableNumber());
  }
}
