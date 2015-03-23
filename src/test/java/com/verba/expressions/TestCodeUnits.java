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

    assert(graphingTests.hash().equals("dbd13781bfc8146f1e1fe206272c720ad039c864"));
    assert(slightlyModifiedGraphingTests.hash().equals("8c42903c98622da0c599d7c7df79ed10f3668bc5"));
  }
}
