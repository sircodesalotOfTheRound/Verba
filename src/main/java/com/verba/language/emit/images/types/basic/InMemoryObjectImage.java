package com.verba.language.emit.images.types.basic;

import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.AppendableObjectImage;
import com.verba.language.emit.images.interfaces.ImageType;
import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 14/9/23.
 */
public class InMemoryObjectImage implements AppendableObjectImage {
  private boolean isFrozen = false;
  private final String name;
  private final ImageType type;
  private final List<Byte> data = new ArrayList<>();
  private byte[] byteData = null;

  public InMemoryObjectImage(String name, ImageType type) {
    this.name = name;
    this.type = type;
  }

  public long size() {
    return this.data.size();
  }


  public byte[] data() {
    if (this.byteData != null) {
      return this.byteData;
    }

    this.byteData = new byte[data.size()];

    for (int index = 0; index < data.size(); index++) {
      byteData[index] = data.get(index);
    }

    // Clear the list since we don't need it any more.
    this.data.clear();
    this.isFrozen = true;

    return byteData;
  }

  @Override
  public ObjectImageOutputStream writeOpCode(VerbatimOpCodeBinaryValue value) {
    for (int index = 0; index < value.opcodeValues().length; index++) {
      if (index == 0) {
        writeInt8(value.opcodeName(), value.opcodeValues()[index]);
      } else {
        writeInt8(null, value.opcodeValues()[index]);
      }
    }
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt8(String label, int value) {
    if (isFrozen) {
      throw new NotImplementedException();
    }

    data.add((byte)value);
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt16(String label, int value) {
    writeInt8(null, (byte)(value & 0xFF));
    writeInt8(null, (byte)((value >> 8) & 0xFF));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt32(String label, int value) {
    writeInt8(null, (byte)(value & 0xFF));
    writeInt8(null, (byte)((value >> 8) & 0xFF));
    writeInt8(null, (byte)((value >> 16) & 0xFF));
    writeInt8(null, (byte)((value >> 24) & 0xFF));
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt64(String label, long value) {
    writeInt8(null, (byte)(value & 0xFF));
    writeInt8(null, (byte)((value >> 8) & 0xFF));
    writeInt8(null, (byte)((value >> 16) & 0xFF));
    writeInt8(null, (byte)((value >> 24) & 0xFF));
    writeInt8(null, (byte)((value >> 32) & 0xFF));
    writeInt8(null, (byte)((value >> 40) & 0xFF));
    writeInt8(null, (byte)((value >> 48) & 0xFF));
    writeInt8(null, (byte)((value >> 56) & 0xFF));

    return this;
  }

  @Override
  public ObjectImageOutputStream writeString(String label, StringTableStringEntry value) {
    writeInt32(null, value.index());
    return this;
  }

  @Override
  public ObjectImageOutputStream writeFqn(String label, StringTableFqnEntry value) {
    writeInt8(label, value.length());

    for (StringTableStringEntry entry : value.entries()) {
      writeString(label, entry);
    }

    return this;
  }

  @Override
  public String name() { return this.name; }


  @Override
  public ImageType imageType() { return this.type; }
}
