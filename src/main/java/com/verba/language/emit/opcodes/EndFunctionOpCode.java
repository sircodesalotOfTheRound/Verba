package com.verba.language.emit.opcodes;

import com.verba.language.emit.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/26.
 */
public class EndFunctionOpCode implements VerbajOpCode {
  @Override
  public int opNumber() { return 0xFF; }
  @Override
  public String opName() { return "EndFunc"; }

  @Override
  public void render(FunctionOpCodeRenderer renderer) { }
}
