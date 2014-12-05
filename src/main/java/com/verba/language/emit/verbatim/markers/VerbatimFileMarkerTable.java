package com.verba.language.emit.verbatim.markers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 14/12/5.
 */
public class VerbatimFileMarkerTable {
  private final Map<String, VerbatimFileMarker> table = new HashMap<>();

  public VerbatimFileMarker insert(String name, long offset) {
    VerbatimFileMarker marker = new VerbatimFileMarker(name, offset);
    this.table.put(name, marker);

    return marker;
  }

  public boolean containsMarkerFor(String name) {
    return this.table.containsKey(name);
  }

  public long findOffsetForMarker(String name) {
    return this.table.get(name).offset();
  }
}
