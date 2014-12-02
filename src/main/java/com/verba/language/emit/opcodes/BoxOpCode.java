package com.verba.language.emit.opcodes;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.rendering.functions.FunctionOpCodeRenderer;

/**
 * Created by sircodesalot on 14/9/23.
 */
public class BoxOpCode extends VerbajOpCodeBase {
  private static final int opNumber = 0x31;
  private static final String opName = "Box";

  private final VirtualVariable source;
  private final VirtualVariable destination;

  public BoxOpCode(VirtualVariable source, VirtualVariable destination) {
    super(opNumber, opName);

    this.source = source;
    this.destination = destination;
  }

  @Override
  public void render(FunctionOpCodeRenderer renderer) {
    renderer.writeInt8("source", source.variableNumber());
    renderer.writeInt8("destination", destination.variableNumber());
  }
}
