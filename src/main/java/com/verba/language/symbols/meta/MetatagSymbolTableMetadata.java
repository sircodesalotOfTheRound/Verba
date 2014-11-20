package com.verba.language.symbols.meta;

import com.verba.language.expressions.categories.MetaTagExpression;
import com.verba.language.symbols.meta.interfaces.SymbolTableMetadata;

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
