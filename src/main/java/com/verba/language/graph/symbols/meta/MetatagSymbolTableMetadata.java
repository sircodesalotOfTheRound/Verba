package com.verba.language.graph.symbols.meta;

import com.verba.language.graph.symbols.meta.interfaces.SymbolTableMetadata;
import com.verba.language.parsing.expressions.categories.MetaTagExpression;

/**
 * Created by sircodesalot on 14-5-13.
 */
public class MetatagSymbolTableMetadata implements SymbolTableMetadata {
  private final MetaTagExpression metatag;

  public MetatagSymbolTableMetadata(MetaTagExpression metatag) {
    this.metatag = metatag;
  }

  public MetaTagExpression metatag() {
    return this.metatag;
  }
}
