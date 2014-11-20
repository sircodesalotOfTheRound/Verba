package com.verba.language.emit.rendering.functions;

/**
 * Created by sircodesalot on 14/9/19.
 */
public interface FunctionOpCodeRenderer {
  void writeInt8(String label, int value);
  void writeInt16(String label, int value);
  void writeInt32(String label, int value);
  void writeInt64(String label, long value);

  void writeString(String label, String value);
}
