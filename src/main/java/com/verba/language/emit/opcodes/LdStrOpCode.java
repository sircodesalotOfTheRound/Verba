package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class LdStrOpCode extends VerbajOpCodeBase {
  private static final int opNumber = 0xD1;
  private static final String opName = "LdStr";

  private final VirtualVariable variable;
  private final String text;

  public LdStrOpCode(VirtualVariable variable, String text) {
    super(opNumber, opName);

    this.variable = variable;
    this.text = text;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("varnum", variable.variableNumber());
    renderer.writeString("text", text);
  }

  public String text() { return this.text; }
  public VirtualVariable variable() { return this.variable; }
}
