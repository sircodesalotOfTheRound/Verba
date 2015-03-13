package com.verba.opcodes;

import com.verba.language.emit.opcodes.binary.VerbatimOpCodeBinaryValue;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sircodesalot on 15/3/13.
 */
public class TestNoDuplicateOpCodes {
  class OpCodeEquator {
    private final String representation;
    private final VerbatimOpCodeBinaryValue value;
    public OpCodeEquator(VerbatimOpCodeBinaryValue value) {
      this.value = value;
      this.representation = determineRepresentation(value);
    }

    public String determineRepresentation(VerbatimOpCodeBinaryValue value) {
      StringBuilder builder = new StringBuilder().append("0x");
      for (int binaryValue : value.opcodeValues()) {
        builder.append(Integer.toHexString(binaryValue)).append(" ");
      }

      return builder.toString().trim();
    }

    @Override
    public int hashCode() {
      return representation.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
      if (rhs == null || !(rhs instanceof OpCodeEquator)) {
        return false;
      } else {
        return this.representation.equals(((OpCodeEquator)rhs).representation);
      }
    }

    public String name() { return value.opcodeName(); }
    public String representation() { return representation; }
  }

  public static class DuplicateException extends RuntimeException {
    public DuplicateException(OpCodeEquator existing, OpCodeEquator duplicate) {
      super(generateMessage(existing, duplicate));
    }

    private static String generateMessage(OpCodeEquator existing, OpCodeEquator duplicate) {
      String message = "Opcodes '%s and '%s' share the same op code: %s";
      return String.format(message, existing.name(), duplicate.name(), existing.representation);
    }
  }

  @Test
  public void testNoDuplicateOpCodes() {
    VerbatimOpCodeBinaryValue[] values = VerbatimOpCodeBinaryValue.values();
    HashMap<String, OpCodeEquator> seen = new HashMap<>();
    for (VerbatimOpCodeBinaryValue current : values) {
      OpCodeEquator equator = new OpCodeEquator(current);
      if(seen.containsKey(equator.representation())) {
        OpCodeEquator existing = seen.get(equator.representation);
        throw new DuplicateException(existing, equator);
      } else {
        seen.put(equator.representation, equator);
      }
    }

    assert(seen.size() == values.length);
  }
}
