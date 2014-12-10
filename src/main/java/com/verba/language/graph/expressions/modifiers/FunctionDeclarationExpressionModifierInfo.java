package com.verba.language.graph.expressions.modifiers;

import com.javalinq.implementations.QSet;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14/12/10.
 */
public class FunctionDeclarationExpressionModifierInfo extends ExpressionModifierInfo<FunctionDeclarationExpression> {
  public FunctionDeclarationExpressionModifierInfo(FunctionDeclarationExpression expression) {
    super(expression);
  }

  @Override
  protected QSet<String> applySpecificModifiers(QSet<String> modifiers) {
    if (!modifiers.contains(KeywordToken.PUBLIC)
      || !modifiers.contains(KeywordToken.PROTECTED)
      || !modifiers.contains(KeywordToken.INTERNAL))
    {
      modifiers.add(KeywordToken.PRIVATE);
    }

    return modifiers;
  }
}
