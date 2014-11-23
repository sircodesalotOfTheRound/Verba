package com.verba.language.emit.opcodes;

import com.verba.language.emit.registers.VirtualVariable;
import com.verba.language.emit.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/22.
 */
public class StageArgOpCode implements VerbajOpCode {
  private int variableNumber;

  public StageArgOpCode(VirtualVariable variable) {
    this.variableNumber = variable.variableNumber();
  }

  @Override
  public int opNumber() { return 0x29; }

  @Override
  public String opName() { return "StgArg"; }

  @Override
  public void render(FunctionOpCodeRenderer renderer) {
    renderer.writeInt8("varnum", variableNumber);
  }
}
