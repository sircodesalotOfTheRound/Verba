package com.verba.language.emit.opcodes;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/19.
 */
public abstract class VerbajOpCodeBase {
  private final int opcodeNumber;
  private final String opcodeName;

  protected VerbajOpCodeBase(int opcodeNumber, String opcodeName) {
    this.opcodeNumber = opcodeNumber;
    this.opcodeName = opcodeName;
  }

  public int opcodeNumber() { return opcodeNumber;}
  public String opcodeName() { return opcodeName; }

  public abstract void render(ObjectImageOutputStream renderer);

  // Static Op-codes
  private static VerbajOpCodeBase endFunctionOpCode = new EndFunctionOpCode();
  private static VerbajOpCodeBase returnOpCode = new RetOpCode();

  // Factory methods
  public static VerbajOpCodeBase box(VirtualVariable source, VirtualVariable destination) {
    return new BoxOpCode(source, destination);
  }

  public static VerbajOpCodeBase call(String functionName) {
    return new CallOpCode(functionName);
  }

  public static VerbajOpCodeBase call(String functionName, Iterable<VirtualVariable> variables) {
    return new CallOpCode(functionName, variables);
  }

  public static VerbajOpCodeBase endFunction() {
    return endFunctionOpCode;
  }

  public static VerbajOpCodeBase ret() {
    return returnOpCode;
  }

  public static VerbajOpCodeBase loadString(VirtualVariable variable, String text) {
    return new LdStrOpCode(variable, text);
  }

  public static VerbajOpCodeBase stageArg(VirtualVariable variable) {
    return new StageArgOpCode(variable);
  }

  public static VerbajOpCodeBase loaduint64(VirtualVariable variable, long value) {
    return new LdUint64OpCode(variable, value);
  }
}
