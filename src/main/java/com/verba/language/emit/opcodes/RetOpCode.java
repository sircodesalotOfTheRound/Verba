package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class RetOpCode extends VerbatimOpCodeBase {
  private static final VerbatimOpCodeBinaryValue RET_NO_VALUE = VerbatimOpCodeBinaryValue.RET_NO_VALUE;
  private static final VerbatimOpCodeBinaryValue RET_WITH_VALUE = VerbatimOpCodeBinaryValue.RET_WITH_VALUE;

  private static final String returnNoValueOpName = "Ret";
  private static final String returnWithValueOpName = "RetV";

  private final VirtualVariable variable;

  public RetOpCode() {
    super(RET_NO_VALUE);
    this.variable = null;
  }
  public RetOpCode(VirtualVariable variable) {
    super(RET_WITH_VALUE);
    this.variable = variable;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    if (this.variable != null) {
      renderer.writeInt8("varnum", variable.variableNumber());
    }
  }

}
