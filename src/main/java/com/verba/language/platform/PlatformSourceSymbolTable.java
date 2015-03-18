package com.verba.language.platform;

import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.platform.expressions.PlatformSourceExpression;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class PlatformSourceSymbolTable extends Scope {
  public static final PlatformSourceSymbolTable INSTANCE = new PlatformSourceSymbolTable();

  public PlatformSourceSymbolTable() {
    super(PlatformSourceExpression.INSTANCE);
  }
}
