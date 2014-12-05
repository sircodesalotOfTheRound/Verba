package com.verba.language.emit.verbatim.persist;

import com.verba.language.emit.images.ObjectImageSet;
import com.verba.language.emit.images.interfaces.ImageType;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.verbatim.markers.VerbatimFileMarkerTable;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class VerbatimFileGenerator {
  public ObjectImageSet images;
  public VerbatimFileMarkerTable markerTable;

  public VerbatimFileGenerator(ObjectImageSet images) {
    this.images = images;
    this.markerTable = new VerbatimFileMarkerTable();
  }

  public boolean save(String path) {

    boolean returnValue = true;
    try (VerbatimFileObjectEmitter emitter = new VerbatimFileObjectEmitter(markerTable, path)) {
      this.emitAllObjectsOfType(emitter, ImageType.FUNCTION);
    } catch (Exception ex) {
      returnValue = false;
    }

    return returnValue;
  }

  private void emitAllObjectsOfType(VerbatimFileObjectEmitter emitter, ImageType imageType) {
    for (ObjectImage image : this.images.ofType(imageType)) {
      emitter.emitObject(image, true);
    }
  }

}
