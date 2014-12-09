package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/12/9.
 */
public class LdBoolOpCode extends VerbatimOpCodeBase {
  private static final int ldTrueOpCode = 0x90;
  private static final int ldFalseOpCode = 0x91;

  private static final String boolTrueOpCode = "ldTrue";
  private static final String boolFalseOpCode = "ldFalse";
  private final VirtualVariable variable;

  public LdBoolOpCode(VirtualVariable variable, boolean value) {
    super(determineOpCodeNumber(value), determineOpCodeName(value));
    this.variable = variable;
  }

  private static int determineOpCodeNumber(boolean value) {
    if (value) {
      return ldTrueOpCode;
    } else {
      return ldFalseOpCode;
    }
  }

  private static String determineOpCodeName(boolean value) {
    if (value) {
      return boolTrueOpCode;
    } else {
      return boolFalseOpCode;
    }
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("variable", variable.variableNumber());
  }
}
