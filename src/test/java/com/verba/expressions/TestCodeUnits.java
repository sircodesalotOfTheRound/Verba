package com.verba.expressions;

import com.verba.language.build.source.CodeUnit;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/10.
 */
public class TestCodeUnits {
  @Test
  public void testLoadSourceUnit() {
    CodeUnit graphingTests = CodeUnit.fromFile("verba_sources/code_unit_tests/GraphingTests.v");
    CodeUnit slightlyModifiedGraphingTests = CodeUnit.fromFile("verba_sources/code_unit_tests/SlightlyModifiedGraphingTests.v");

    assert(graphingTests.path().equals("verba_sources/code_unit_tests/GraphingTests.v"));
    assert(slightlyModifiedGraphingTests.path().equals("verba_sources/code_unit_tests/SlightlyModifiedGraphingTests.v"));

    assert(graphingTests.hash().equals("de85049d2a898c3cbf978d0ece016fd26cb34b13"));
    assert(slightlyModifiedGraphingTests.hash().equals("4e5fce4fdb54e992efa4d1ed338a441c4eb79a3a"));
  }
}
