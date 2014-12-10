package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/23.
 */
public class CopyOpCode extends VerbatimOpCodeBase {
  private static final int opNumber = 0x33;
  private static final String opName = "Cpy";

  private final VirtualVariable source;
  private final VirtualVariable destination;

  public CopyOpCode(VirtualVariable destination, VirtualVariable source) {
    super(opNumber, opName);

    this.source = source;
    this.destination = destination;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeInt8("destvar", destination.variableNumber());
    renderer.writeInt8("srcvar", source.variableNumber());
  }
}
