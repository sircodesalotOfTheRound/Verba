package com.verba.language.emit.opcodes;

import com.verba.language.emit.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/19.
 */
public interface VerbajOpCode {
  int opNumber();
  String opName();
  void render(FunctionOpCodeRenderer renderer);
}
