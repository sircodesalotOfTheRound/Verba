package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;

/**
 * Created by sircodesalot on 14/9/26.
 */
public class EndFunctionOpCode extends VerbatimOpCodeBase {
  private static final VerbatimOpCodeBinaryValue END_FUNCTION = VerbatimOpCodeBinaryValue.END_FUNCTION;

  public EndFunctionOpCode() {
    super(END_FUNCTION);
  }

  @Override
  public void render(ObjectImageOutputStream renderer) { }
}
