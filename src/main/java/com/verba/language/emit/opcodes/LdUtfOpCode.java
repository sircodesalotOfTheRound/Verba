package com.verba.language.emit.opcodes;

import com.verba.language.emit.header.stringtable.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class LdUtfOpCode extends VerbatimOpCodeBase {
  private static final VerbatimOpCodeBinaryValue LD_UTF = VerbatimOpCodeBinaryValue.LD_UTF;

  private final VirtualVariable variable;
  private final StringTableStringEntry string;

  public LdUtfOpCode(VirtualVariable variable, StringTableStringEntry string) {
    super(LD_UTF);

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
