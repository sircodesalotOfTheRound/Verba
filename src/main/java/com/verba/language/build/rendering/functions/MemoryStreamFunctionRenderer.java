package com.verba.language.build.rendering.functions;

import com.verba.language.graph.imagegen.function.FunctionGraph;
import com.verba.language.build.opcodes.VerbajOpCode;
import com.verba.language.build.rendering.images.ImageRenderer;
import com.verba.language.build.rendering.images.ImageType;
import com.verba.language.build.rendering.images.ObjectImage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 14/9/23.
 */
public class MemoryStreamFunctionRenderer implements FunctionOpCodeRenderer, ObjectImage {
  private boolean isFrozen = false;
  private final String name;
  private final Iterable<VerbajOpCode> opcodes;
  private final List<Byte> data = new ArrayList<>();
  private byte[] byteData = null;

  public MemoryStreamFunctionRenderer(FunctionGraph functionGraph) {
    this.name = functionGraph.name();
    this.opcodes = functionGraph.opcodes();

    this.generateOpCodeList();
  }

  public int size() { return this.data.size(); }

  private void generateOpCodeList() {
    // Write the name of the function
    writeString("name", this.name);

    for (VerbajOpCode opCode : opcodes) {
      writeInt8(null, opCode.opNumber());
      opCode.render(this);
    }
  }

  public byte[] asArray() {
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
  public void writeInt8(String label, int value) {
    if (isFrozen) {
      throw new NotImplementedException();
    }

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

  public void displayCoreDump() {
    byte[] byteData = this.asArray();
    System.out.println(String.format("Image: %s (%s bytes)", this.name, this.byteData.length));

    int count = 0;
    for (byte data : byteData) {
      if (count++ > 0 && count % 10 == 0) {
        System.out.println();
      }

      System.out.print(String.format("%02x ", data));
    }
  }

  @Override
  public void accept(ImageRenderer renderer) {
    renderer.visit(this);
  }

  @Override
  public ImageType imageType() { return ImageType.FUNCTION; }
}
