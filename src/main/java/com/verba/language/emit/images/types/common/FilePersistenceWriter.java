package com.verba.language.emit.images.types.common;

import com.verba.language.emit.header.stringtable.StringTableFqnEntry;
import com.verba.language.emit.header.stringtable.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import com.verba.tools.exceptions.CompilerException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sircodesalot on 15/3/23.
 */
public class FilePersistenceWriter implements ObjectImageOutputStream, AutoCloseable {
  private final File path;
  private final FileOutputStream stream;

  public FilePersistenceWriter(File path) {
    this.path = path;
    this.stream = loadFile(path);
  }

  public FileOutputStream loadFile(File path) {
    try {
      return new FileOutputStream(path.getAbsoluteFile());
    } catch (IOException ex) {
      throw new CompilerException("Unable to load file");
    }
  }

  @Override
  public ObjectImageOutputStream writeOpCode(VerbatimOpCodeBase value) {
    for (int opcode : value.opcodeBinaryValues()) {
      this.writeInt8(value.opcodeName(), opcode);
    }

    return this;
  }

  public ObjectImageOutputStream writeImage(String label, ObjectImage image) {
    try {
      stream.write(image.data());
    } catch (IOException ex) {
      throw new CompilerException("Error occured during write");
    }

    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt8(String label, int value) {
    try {
      stream.write((byte) value);
    } catch (IOException ex) {
      throw new CompilerException("Error occured during write");
    }
    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt16(String label, int value) {
    this.writeInt8(label, value & 0xFF);
    this.writeInt8(label, (value >> 8) & 0xFF);

    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt32(String label, int value) {
    this.writeInt8(label, value & 0xFF);
    this.writeInt8(label, (value >> 8) & 0xFF);
    this.writeInt8(label, (value >> 16) & 0xFF);
    this.writeInt8(label, (value >> 24) & 0xFF);

    return this;
  }

  @Override
  public ObjectImageOutputStream writeInt64(String label, long value) {
    this.writeInt8(label, (int)(value & 0xFF));
    this.writeInt8(label, (int)((value >> 8) & 0xFF));
    this.writeInt8(label, (int)((value >> 16) & 0xFF));
    this.writeInt8(label, (int)((value >> 24) & 0xFF));
    this.writeInt8(label, (int)((value >> 32) & 0xFF));
    this.writeInt8(label, (int)((value >> 40) & 0xFF));
    this.writeInt8(label, (int)((value >> 48) & 0xFF));
    this.writeInt8(label, (int)((value >> 56) & 0xFF));

    return this;
  }

  public ObjectImageOutputStream writeRawString(String label, String text) {
    this.writeInt16(label, text.length());
    for (char letter : text.toCharArray()) {
      this.writeInt8(label, letter);
    }

    return this;
  }

  @Override
  public ObjectImageOutputStream writeString(String label, StringTableStringEntry value) {
    this.writeInt32(label, value.index());

    return this;
  }

  @Override
  public ObjectImageOutputStream writeFqn(String label, StringTableFqnEntry value) {
    this.writeInt8(label, value.length());
    for (StringTableStringEntry entry : value.entries()) {
      this.writeString(label, entry);
    }

    return this;
  }

  @Override
  public void close() throws Exception {
    stream.close();
  }
}
