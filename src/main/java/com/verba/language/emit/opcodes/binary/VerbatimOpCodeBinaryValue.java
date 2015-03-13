package com.verba.language.emit.opcodes.binary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/3/13.
 */
public enum VerbatimOpCodeBinaryValue {
  CALL_NO_RETAIN("Call", "Call discarding result", 0x43),
  CALL_WITH_RETAIN("CallRt", "Call retaining result", 0x43),

  BOX("Box", "Box constant value", 0x31),
  COPY("Copy", "Copy variable", 0x33),
  RET_NO_VALUE("RetNV", "Return unit", 0xC7),
  RET_WITH_VALUE("RetV", "Return with a value", 0xC8),

  LD_TRUE("LdTrue", "Load 'True'", 0x90),
  LD_FALSE("LdFalse", "Load 'False'", 0x91),
  END_FUNCTION("EndF", "End of function", 0xFF),


  STAGE_ARG("StgArg", "Stage argument for call", 0x29),
  LD_UI64("LdUi64", "Load 64 bit unsigned int", 0xD3),
  LD_STR("LdStr", "Load String", 0xD1);

  private final String opcodeName;
  private final String description;
  private final int[] opcodeValues;
  VerbatimOpCodeBinaryValue(String opcodeName, String description, long binaryValue) {
    this.opcodeName = opcodeName;
    this.description = description;
    this.opcodeValues = parseValues(binaryValue);
  }

  public int[] parseValues(long binaryValue) {
    List<Integer> valuesList = new ArrayList<>();
    while (binaryValue > 0) {
      valuesList.add((int)(binaryValue & 0xFF));
      binaryValue = binaryValue >> 8;
    }

    Object[] boxedValues = valuesList.toArray();
    int[] unboxedValues = new int[boxedValues.length];
    for (int index = 0; index < boxedValues.length; index++) {
      unboxedValues[index] = (int)boxedValues[index];
    }

    return unboxedValues;
  }

  public String opcodeName() { return this.opcodeName; }
  public String description() { return this.description; }
  public int[] opcodeValues() { return this.opcodeValues; }
}
