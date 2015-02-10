package com.verba;

import com.verba.language.build.source.SourceUnit;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/10.
 */
public class SourceUnitTests {
  @Test
  public void testLoadSourceUnit() {
    SourceUnit graphingTests = SourceUnit.fromFile("TestSources/SourceUnitTests/GraphingTests.v");
    SourceUnit slightlyModifiedGraphingTests = SourceUnit.fromFile("TestSources/SourceUnitTests/SlightlyModifiedGraphingTests.v");

    assert(graphingTests.hash().equals("de85049d2a898c3cbf978d0ece016fd26cb34b13"));
    assert(slightlyModifiedGraphingTests.hash().equals("4e5fce4fdb54e992efa4d1ed338a441c4eb79a3a"));
  }
}
