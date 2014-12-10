package com.verba.language.graph.expressions.modifiers;

import com.javalinq.implementations.QSet;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/10.
 */
public class PolymorphicExpressionModifierInfo extends ExpressionModifierInfo<PolymorphicDeclarationExpression> {
  public PolymorphicExpressionModifierInfo(PolymorphicDeclarationExpression expression) {
    super(expression);
  }

  @Override
  protected QSet<String> applySpecificModifiers(QSet<String> modifiers) {
    if (!modifiers.contains(KeywordToken.PUBLIC)
      || !modifiers.contains(KeywordToken.PRIVATE)
      || !modifiers.contains(KeywordToken.PROTECTED))
    {
      modifiers.add(KeywordToken.INTERNAL);
    }

    return modifiers;
  }
}
