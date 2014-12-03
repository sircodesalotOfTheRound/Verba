package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class RetOpCode extends VerbajOpCodeBase {
  private static final int opNumber = 0xC7;
  private static final String opName = "Ret";

  public RetOpCode() {
    super(opNumber, opName);
  }

  @Override
  public void render(ObjectImageOutputStream renderer) { /* The op will automatically be written*/ }

}
