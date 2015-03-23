package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 15/3/23.
 */
public class AddOpCode extends VerbatimOpCodeBase {
  private static VerbatimOpCodeBinaryValue ADD = VerbatimOpCodeBinaryValue.ADD;

  private final VirtualVariable destination;
  private final VirtualVariable source;

  protected AddOpCode(VirtualVariable destination, VirtualVariable source) {
    super(ADD);
    this.destination = destination;
    this.source = source;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("destination", destination.variableNumber());
    renderer.writeInt8("source", source.variableNumber());
  }
}
