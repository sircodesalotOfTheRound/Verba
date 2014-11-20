package com.verba.language.emit.rendering.functions;

import com.verba.language.emit.opcodes.VerbajOpCode;
import com.verba.language.exceptions.CompilerException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 14/9/23.
 */
public class FileImageOpcodeRenderer implements FunctionOpCodeRenderer, AutoCloseable {
  private final FileOutputStream stream;
  private final Iterable<VerbajOpCode> opcodes;
  private final List<Byte> data = new ArrayList<>();
  private final String name;

  public FileImageOpcodeRenderer(String imageName, String path, Iterable<VerbajOpCode> opcodes) {
    this.name = imageName;
    this.opcodes = opcodes;
    try {
      this.stream = new FileOutputStream(path);

    } catch (IOException ex) {
      throw new CompilerException("Unable to open output file");
    }
  }

  public void save() throws Exception {
    List<Byte> renderedContent = renderOpCodes();
    byte[] content = toArray(renderedContent);
    stream.write(content, 0, content.length);
  }

  private List<Byte> renderOpCodes() {
    writeString("name", this.name);

    for (VerbajOpCode opCode : opcodes) {
      writeInt8(null, opCode.opNumber());
      opCode.render(this);
    }

    return this.data;
  }

  private byte[] toArray(List<Byte> data) {
    byte[] content = new byte[data.size()];

    for (int index = 0; index < data.size(); index++) {
      content[index] = data.get(index);
    }

    return content;
  }

  @Override
  public void writeInt8(String label, int value) {
    data.add((byte)value);
  }

  @Override
  public void writeInt16(String label, int value) {
    writeInt8(null, (byte)(value & 0xFF));
    writeInt8(null, (byte)((value >> 8) & 0xFF));
  }

  @Override
  public void writeInt32(String label, int value) {
    writeInt8(null, (byte)(value & 0xFF));
    writeInt8(null, (byte)((value >> 8) & 0xFF));
    writeInt8(null, (byte)((value >> 16) & 0xFF));
    writeInt8(null, (byte)((value >> 24) & 0xFF));
  }

  @Override
  public void writeInt64(String label, long value) {
    writeInt8(null, (byte)(value & 0xFF));
    writeInt8(null, (byte)((value >> 8) & 0xFF));
    writeInt8(null, (byte)((value >> 16) & 0xFF));
    writeInt8(null, (byte)((value >> 24) & 0xFF));
    writeInt8(null, (byte)((value >> 32) & 0xFF));
    writeInt8(null, (byte)((value >> 40) & 0xFF));
    writeInt8(null, (byte)((value >> 48) & 0xFF));
    writeInt8(null, (byte)((value >> 56) & 0xFF));
  }

  @Override
  public void writeString(String label, String value) {
      writeInt32(null, value.length());

      for (byte letter : value.getBytes()) {
        writeInt8(null, letter);
      }
  }

  public String name() { return this.name; }

  @Override
  public void close() throws Exception {
    stream.close();
  }
}
