package com.verba.language.emit.images.types.basic;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.header.StringTableStringEntry;
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
  public ObjectImageOutputStream writeInt8(String label, int value) {
    printlnFormatted("\t[%s] \t%s", label, asHex(value));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt16(String label, int value) {
    printlnFormatted("\t[%s] \t%s", label, asHex(value));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt32(String label, int value) {
    printlnFormatted("\t[%s] \t%s", label, asHex(value));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt64(String label, long value) {
    printlnFormatted("\t[%s] \t%s", label, asHex(value));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeString(String label, StringTableStringEntry value) {
    printlnFormatted("\t[%s: %s] \t[index: %s]", label, value.text(), value.index());
    return this;
  }

  @Override
  public ObjectImageOutputStream writeFqn(String label, StringTableFqnEntry value) {
    QIterable<String> fqnStringIndexes = value.entries()
      .map(StringTableStringEntry::index)
      .map(Object::toString);

    String joinedFqnString = String.join(".", fqnStringIndexes);
    printlnFormatted("\t[%s: %s] \t[fqn: %s]", label, value.text(), joinedFqnString);

    return this;
  }

  private void printFormatted(String format, Object ... args) {
    System.out.print(String.format(format, args));
  }

  private void printlnFormatted(String format, Object... args) {
    printFormatted(format, args);
  }

  private String asHex(long value) {
    return String.format("%02x ", value);
  }
}
