package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;

/**
 * Created by sircodesalot on 14/9/26.
 */
public class EndFunctionOpCode extends VerbajOpCodeBase {
  private static final int opNumber = 0xFF;
  private static final String opName = "EndFunc";

  public EndFunctionOpCode() {
    super(opNumber, opName);
  }

  @Override
  public void render(ObjectImageOutputStream renderer) { }
}
