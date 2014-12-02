package com.verba.language.emit.opcodes;

import com.javalinq.implementations.QList;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class CallOpCode extends VerbajOpCodeBase {
  private static final int opNumber = 0x43;
  private static final String opName = "Call";

  public String function;
  public Iterable<VirtualVariable> variables;

  public CallOpCode(String function, Iterable<VirtualVariable> variables) {
    super(opNumber, opName);

    this.function = function;
    this.variables = variables;
  }

  public CallOpCode(String function) {
    this(function, new QList<>());
  }

  @Override
  public void render(FunctionOpCodeRenderer renderer) {
    renderer.writeString("function_name", function);
  }

  public String function() {
    return this.function;
  }
}
