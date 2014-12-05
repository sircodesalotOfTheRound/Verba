package com.verba.language.emit.images.interfaces;

import com.verba.language.emit.header.StringTableEntry;

/**
 * Created by sircodesalot on 14/9/19.
 */
public interface ObjectImageOutputStream {
  ObjectImageOutputStream writeInt8(String label, int value);
  ObjectImageOutputStream writeInt16(String label, int value);
  ObjectImageOutputStream writeInt32(String label, int value);
  ObjectImageOutputStream writeInt64(String label, long value);

  ObjectImageOutputStream writeString(String label, StringTableEntry value);

  default ObjectImage asObjectImage() { return (ObjectImage)this; }
}
