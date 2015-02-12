package com.verba;

import com.verba.language.build.source.CodeUnit;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/10.
 */
public class TestCodeUnits {
  @Test
  public void testLoadSourceUnit() {
    CodeUnit graphingTests = CodeUnit.fromFile("TestSources/SourceUnitTests/GraphingTests.v");
    CodeUnit slightlyModifiedGraphingTests = CodeUnit.fromFile("TestSources/SourceUnitTests/SlightlyModifiedGraphingTests.v");

    assert(graphingTests.path().equals("TestSources/SourceUnitTests/GraphingTests.v"));
    assert(slightlyModifiedGraphingTests.path().equals("TestSources/SourceUnitTests/SlightlyModifiedGraphingTests.v"));

    assert(graphingTests.hash().equals("de85049d2a898c3cbf978d0ece016fd26cb34b13"));
    assert(slightlyModifiedGraphingTests.hash().equals("4e5fce4fdb54e992efa4d1ed338a441c4eb79a3a"));
  }
}
