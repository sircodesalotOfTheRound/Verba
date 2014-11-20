package com.verba.language.graph.symbols.resolution.metatags;

import com.verba.language.parsing.expressions.categories.MetaTagExpression;
import com.verba.language.parsing.expressions.tags.aspect.AspectTagExpression;
import com.verba.language.parsing.expressions.tags.hashtag.HashTagExpression;
import com.verba.language.graph.symbols.resolution.interfaces.SymbolResolver;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;

/**
 * Created by sircodesalot on 14-5-16.
 */
public class MetatagResolver implements SymbolResolver<MetaTagExpression, MetaTagResolutionInfo> {
  private final HashtagResolver hashtagResolver;
  private final AspectTagResolver aspectTagResolver;

  public MetatagResolver(GlobalSymbolTable table) {
    this.hashtagResolver = new HashtagResolver(table);
    this.aspectTagResolver = new AspectTagResolver(table);
  }

  @Override
  public MetaTagResolutionInfo resolve(MetaTagExpression expression) {
    if (expression instanceof HashTagExpression)
      return hashtagResolver.resolve((HashTagExpression) expression);
    else
      return aspectTagResolver.resolve((AspectTagExpression) expression);
  }
}
