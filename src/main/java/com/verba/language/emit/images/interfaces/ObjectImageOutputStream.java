package com.verba.language.emit.images.interfaces;

import com.verba.language.emit.header.StringTableFqnEntry;
import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;

/**
 * Created by sircodesalot on 14/9/19.
 */
public interface ObjectImageOutputStream {
  ObjectImageOutputStream writeOpCode(VerbatimOpCodeBinaryValue value);
  ObjectImageOutputStream writeInt8(String label, int value);
  ObjectImageOutputStream writeInt16(String label, int value);
  ObjectImageOutputStream writeInt32(String label, int value);
  ObjectImageOutputStream writeInt64(String label, long value);

  ObjectImageOutputStream writeString(String label, StringTableStringEntry value);
  ObjectImageOutputStream writeFqn(String label, StringTableFqnEntry value);

  default ObjectImage asObjectImage() { return (ObjectImage)this; }
}
