package com.verba.language.emit.images.types.basic;

import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class DebuggingObjectImage implements ObjectImageOutputStream {
  private final Iterable<VerbatimOpCodeBase> opcodes;

  public DebuggingObjectImage(Iterable<VerbatimOpCodeBase> opcodes) {
    this.opcodes = opcodes;
  }

  public void display() {
    for (VerbatimOpCodeBase opcode : opcodes) {
      String prefix = String.format("(0x%s) %s: ", Integer.toHexString(opcode.opcodeNumber()), opcode.opcodeName());
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
