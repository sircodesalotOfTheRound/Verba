package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class RetOpCode extends VerbatimOpCodeBase {
  private static final int returnNoValue = 0xC7;
  private static final int returnWithValue = 0xC8;

  private static final String returnNoValueOpName = "Ret";
  private static final String returnWithValueOpName = "RetV";

  private final VirtualVariable variable;

  public RetOpCode() {
    super(returnNoValue, returnNoValueOpName);
    this.variable = null;
  }
  public RetOpCode(VirtualVariable variable) {
    super(returnWithValue, returnWithValueOpName);
    this.variable = variable;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    if (this.variable != null) {
      renderer.writeInt8("return-index", variable.variableNumber());
    }
  }

}
