package com.verba.language.codegen.opcodes;

import com.verba.language.codegen.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/19.
 */
public interface VerbajOpCode {
  int opNumber();
  String opName();
  void render(FunctionOpCodeRenderer renderer);
}
