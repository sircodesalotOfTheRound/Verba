package com.verba.language.emit.images.types.common;

import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.header.stringtable.StringTableFqnEntry;
import com.verba.language.emit.header.stringtable.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;

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
      String prefix = String.format("(0x%s) %s: ", concatenateOpCodeBinaryValue(opcode.opcodeBinaryValues()), opcode.opcodeName());
      System.out.print(prefix);
      opcode.render(this);
      System.out.println();
    }
  }

  @Override
  public ObjectImageOutputStream writeOpCode(VerbatimOpCodeBase value) {
    printFormatted("\t[%s] \t", value.opcodeName());
    for (int binaryValue : value.opcodeBinaryValues()) {
      printFormatted(String.format("%s ", binaryValue));
    }

    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt8(String label, int value) {
    printFormatted("\t[%s] \t%s", label, asHex(value));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt16(String label, int value) {
    printFormatted("\t[%s] \t%s", label, asHex(value));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt32(String label, int value) {
    printFormatted("\t[%s] \t%s", label, asHex(value));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt64(String label, long value) {
    printFormatted("\t[%s] \t%s", label, asHex(value));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeString(String label, StringTableStringEntry value) {
    printFormatted("\t[%s: %s] \t[string-index: %s]", label, value.text(), value.index());
    return this;
  }

  @Override
  public ObjectImageOutputStream writeFqn(String label, StringTableFqnEntry value) {
    QIterable<String> fqnStringIndexes = value.entries()
      .map(StringTableStringEntry::index)
      .map(Object::toString);

    String joinedFqnString = String.join(".", fqnStringIndexes);
    printFormatted("\t[%s: %s] \t[fqn: %s]", label, value.text(), joinedFqnString);

    return this;
  }

  private String concatenateOpCodeBinaryValue(int[] binaryValues) {
    StringBuilder builder = new StringBuilder();
    for (int value : binaryValues) {
      if (builder.length() > 0) {
        builder.append(" ");
      } else {
        builder.append(Integer.toHexString(value));
      }
    }

    return builder.toString();
  }

  private void printFormatted(String format, Object... args) {
    String message = String.format(format, args);
    System.out.print(message);
  }

  private String asHex(long value) {
    return String.format("%02x ", value);
  }
}
