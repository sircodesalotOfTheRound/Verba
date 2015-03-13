package com.verba.language.emit.opcodes;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/19.
 */
public abstract class VerbatimOpCodeBase {
  private final VerbatimOpCodeBinaryValue value;

  protected VerbatimOpCodeBase(VerbatimOpCodeBinaryValue value) {
    this.value = value;
  }

  public int[] opcodeBinaryValues() { return value.opcodeValues();}
  public String opcodeName() { return value.opcodeName(); }

  public abstract void render(ObjectImageOutputStream renderer);

  // Static Op-codes
  private static VerbatimOpCodeBase endFunctionOpCode = new EndFunctionOpCode();
  private static VerbatimOpCodeBase returnOpCode = new RetOpCode();

  // Factory methods
  public static VerbatimOpCodeBase box(VirtualVariable source, VirtualVariable destination) {
    return new BoxOpCode(source, destination);
  }

  public static VerbatimOpCodeBase call(StringTableFqnEntry functionName, QIterable<VirtualVariable> variables) {
    for (VirtualVariable variable : variables) {
      VerbatimOpCodeBase.stageArg(variable);
    }

    return new CallOpCode(functionName);
  }

  public static VerbatimOpCodeBase call(StringTableFqnEntry functionName,
                                        Iterable<VirtualVariable> variables,
                                        VirtualVariable returnValue) {

    for (VirtualVariable variable : variables) {
      VerbatimOpCodeBase.stageArg(variable);
    }

    return new CallOpCode(functionName, returnValue);
  }

  public static VerbatimOpCodeBase endFunction() {
    return endFunctionOpCode;
  }

  public static VerbatimOpCodeBase ret() {
    return returnOpCode;
  }

  public static VerbatimOpCodeBase ret(VirtualVariable variable) {
    return new RetOpCode(variable);
  }

  public static VerbatimOpCodeBase loadString(VirtualVariable variable, StringTableStringEntry value) {
    return new LdStrOpCode(variable, value);
  }

  public static VerbatimOpCodeBase stageArg(VirtualVariable variable) {
    return new StageArgOpCode(variable);
  }

  public static VerbatimOpCodeBase loaduint64(VirtualVariable variable, long value) {
    return new LdUint64OpCode(variable, value);
  }

  public static VerbatimOpCodeBase loadBoolean(VirtualVariable variable, boolean value) {
    return new LdBoolOpCode(variable, value);
  }

  public static VerbatimOpCodeBase copy(VirtualVariable destination, VirtualVariable source) {
    return new CopyOpCode(destination, source);
  }
}
