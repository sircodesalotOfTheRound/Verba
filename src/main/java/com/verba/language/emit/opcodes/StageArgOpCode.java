package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/22.
 */
public class StageArgOpCode extends VerbajOpCodeBase {
  private static final int opNumber = 0x29;
  private static final String opName = "StgArg";

  private int variableNumber;

  public StageArgOpCode(VirtualVariable variable) {
    super(opNumber, opName);

    this.variableNumber = variable.variableNumber();
  }

  private StageArgOpCode() {
    super(opNumber, opName);
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("varnum", variableNumber);
  }
}
