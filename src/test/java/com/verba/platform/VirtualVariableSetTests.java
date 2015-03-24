package com.verba.platform;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.emit.variables.VirtualVariableSet;
import com.verba.language.platform.PlatformTypeSymbols;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/24.
 */
public class VirtualVariableSetTests {
  @Test
  public void testAddVariable() {
    VirtualVariableSet variables = new VirtualVariableSet();
    variables.create("variable", PlatformTypeSymbols.UNIT);

    assert(variables.variableCount() == 1);
  }

  @Test
  public void testRenameVariable() {
    VirtualVariableSet variables = new VirtualVariableSet();
    VirtualVariable variable = variables.create("variable", PlatformTypeSymbols.UNIT);
    variable.rename("renamed");

    assert(variables.variableCount() == 1);
    assert(!variables.containsKey("variable"));
    assert(variables.containsKey("renamed"));

    VirtualVariable renamed = variables.get("renamed");
    assert (renamed == variable);
  }
}
