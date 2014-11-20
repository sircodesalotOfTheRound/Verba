package com.verba.language.codegen.opcodes;

import com.javalinq.implementations.QList;
import com.verba.language.codegen.registers.VirtualVariable;
import com.verba.language.codegen.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class CallOpCode implements VerbajOpCode {
  public String function;
  public Iterable<VirtualVariable> variables;

  public CallOpCode(String function, Iterable<VirtualVariable> variables) {
    this.function = function;
    this.variables = variables;
  }

  public CallOpCode(String function) {
    this(function, new QList<>());
  }

  @Override
  public int opNumber() { return 0x43; }

  @Override
  public String opName() {
    return "Call";
  }

  @Override
  public void render(FunctionOpCodeRenderer renderer) {
    renderer.writeString("function_name", function);
  }

  public String function() {
    return this.function;
  }
}
