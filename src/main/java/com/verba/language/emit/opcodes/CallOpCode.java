package com.verba.language.emit.opcodes;

import com.javalinq.implementations.QList;
import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class CallOpCode extends VerbatimOpCodeBase {
  private static final VerbatimOpCodeBinaryValue CALL_NO_RETAIN = VerbatimOpCodeBinaryValue.CALL_NO_RETAIN;
  private static final VerbatimOpCodeBinaryValue CALL_WITH_RETAIN = VerbatimOpCodeBinaryValue.CALL_WITH_RETAIN;

  private final StringTableFqnEntry function;
  private final VirtualVariable storeLocation;

  public CallOpCode(StringTableFqnEntry function) {
    super(CALL_NO_RETAIN);

    this.function = function;
    this.storeLocation = null;
  }

  public CallOpCode(StringTableFqnEntry function, VirtualVariable storeLocation) {
    super(CALL_WITH_RETAIN);

    this.function = function;
    this.storeLocation = storeLocation;
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeFqn("function-name", function);

    if (this.storeLocation != null) {
      int variableNumber = storeLocation.variableNumber();
      renderer.writeInt8("store-location", variableNumber);
    }
  }

  public StringTableFqnEntry function() { return this.function; }
}
