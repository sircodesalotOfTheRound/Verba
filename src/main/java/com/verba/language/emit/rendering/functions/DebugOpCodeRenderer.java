package com.verba.language.emit.rendering.functions;

import com.verba.language.emit.opcodes.VerbajOpCode;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class DebugOpCodeRenderer implements FunctionOpCodeRenderer {
  private final Iterable<VerbajOpCode> opcodes;

  public DebugOpCodeRenderer(Iterable<VerbajOpCode> opcodes) {
    this.opcodes = opcodes;
  }

  public void display() {
    for (VerbajOpCode opcode : opcodes) {
      String prefix = String.format("(0x%s) %s: ", Integer.toHexString(opcode.opNumber()), opcode.opName());
      System.out.print(prefix);
      opcode.render(this);
      System.out.println();
    }
  }

  @Override
  public void writeInt8(String label, int value) {
    printFormatted("\t[%s] \t%s", label, asHex(value));

  }

  @Override
  public void writeInt16(String label, int value) {
    printFormatted("\t[%s] \t%s", label, asHex(value));
  }

  @Override
  public void writeInt32(String label, int value) {
    printFormatted("\t[%s] \t%s", label, asHex(value));
  }

  @Override
  public void writeInt64(String label, long value) {
    printFormatted("\t[%s] \t%s", label, asHex(value));
  }

  @Override
  public void writeString(String label, String value) {
    printFormatted("\t[length:%s] \t%s%s", label, asHex(value.length()), value);
  }

  private void printFormatted(String format, Object ... args) {
    System.out.print(String.format(format, args));
  }

  private String asHex(long value) {
    return String.format("%02x ", value);
  }
}
