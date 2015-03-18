package com.verba.language.emit.verbatim.persist;

import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.verbatim.markers.VerbatimFileMarkerTable;
import com.verba.tools.exceptions.CompilerException;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sircodesalot on 14/12/5.
 */
public class VerbatimFileObjectEmitter implements AutoCloseable {
  private final FileOutputStream stream;
  private final VerbatimFileMarkerTable markerTable;
  private long offset;

  public VerbatimFileObjectEmitter(VerbatimFileMarkerTable markerTable, String path) {
    this.markerTable = markerTable;
    this.stream = openFile(path);
    this.offset = 0;
  }

  private FileOutputStream openFile(String path) {
    try { return new FileOutputStream(path); }
    catch (IOException ex) { throw new CompilerException("Unable to open file for writing"); }
  }

  public void resetMarkerOffset() {
    this.offset = 0;
  }

  public void emitObject(ObjectImage image, boolean markOffset) {
    try {
      // (1) Create a marker for this object.
      if (markOffset) {
        this.markerTable.insert(image.name(), this.offset);
      }

      // (2) Emit the object.
      stream.write(image.data());
      this.offset += image.size();
    }
    catch (IOException ex) {
      throw new CompilerException("Unable to persist object to disk");
    }
  }

  @Override
  public void close() throws Exception {
    this.stream.close();
  }
}
