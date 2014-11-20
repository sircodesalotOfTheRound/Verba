package com.verba.language.build.opcodes;

import com.verba.language.build.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class RetOpCode implements VerbajOpCode {

  @Override
  public int opNumber() { return 0xc7; }

  @Override
  public String opName() { return "Ret"; }

  @Override
  public void render(FunctionOpCodeRenderer renderer) { /* The op will automatically be written*/ }

}
