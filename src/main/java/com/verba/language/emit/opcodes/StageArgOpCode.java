package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/22.
 */
public class StageArgOpCode extends VerbatimOpCodeBase {
  private static final VerbatimOpCodeBinaryValue STAGE_ARG = VerbatimOpCodeBinaryValue.STAGE_ARG;
  private int variableNumber;

  public StageArgOpCode(VirtualVariable variable) {
    super(STAGE_ARG);

    this.variableNumber = variable.variableNumber();
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("varnum", variableNumber);
  }
}
