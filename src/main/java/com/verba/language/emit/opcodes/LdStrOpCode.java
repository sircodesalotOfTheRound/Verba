package com.verba.language.emit.opcodes;

import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class LdStrOpCode extends VerbatimOpCodeBase {
  private static final int opNumber = 0xD1;
  private static final String opName = "LdStr";

  private final VirtualVariable variable;
  private final StringTableStringEntry string;

  public LdStrOpCode(VirtualVariable variable, StringTableStringEntry string) {
    super(opNumber, opName);

    this.variable = variable;
    this.string = string;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("varnum", variable.variableNumber());
    renderer.writeString("text", string);
  }

  public String text() { return this.string.text(); }
  public int index() { return this.string.index(); }
  public VirtualVariable variable() { return this.variable; }
}
