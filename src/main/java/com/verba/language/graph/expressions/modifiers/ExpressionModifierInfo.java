package com.verba.language.graph.expressions.modifiers;

import com.javalinq.implementations.QSet;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/12/10.
 */
public abstract class ExpressionModifierInfo<T extends VerbaExpression> {
  private boolean isPublic;
  private boolean isPrivate;
  private boolean isProtected;
  private boolean isInternal;

  // Factory
  protected ExpressionModifierInfo(T expression) {
    // First determine the modifiers to apply.
    QSet<String> modifiers = findExplicitModifiers(expression, new QSet<>());
    modifiers = this.applySpecificModifiers(modifiers);

    // Then set the accessors.
    this.isPublic = modifiers.contains(KeywordToken.PUBLIC);
    this.isPrivate = modifiers.contains(KeywordToken.PRIVATE);
    this.isProtected = modifiers.contains(KeywordToken.PROTECTED);
    this.isInternal = modifiers.contains(KeywordToken.INTERNAL);
  }

  // Helper methods
  private QSet<String> findExplicitModifiers(VerbaExpression expression, QSet<String> modifiers) {
    if (expression == null) {
      return modifiers;
    }

    if (expression.is(DeclarationModifierExrpression.class)) {
      DeclarationModifierExrpression modifier = (DeclarationModifierExrpression)expression;
      modifiers.add(modifier.modifier().representation());
      return this.findExplicitModifiers(expression.parent(), modifiers);
    } else {
      return modifiers;
    }
  }

  public ExpressionModifierInfo read(VerbaExpression expression) {
    if (expression instanceof PolymorphicDeclarationExpression) {
      return new PolymorphicExpressionModifierInfo((PolymorphicDeclarationExpression)expression);
    } else {
      throw new NotImplementedException();
    }
  }

  protected abstract QSet<String> applySpecificModifiers(QSet<String> explicitModifiers);

  boolean isPublic() { return this.isPublic; }
  boolean isPrivate() { return this.isPrivate; }
  boolean isProtected() { return this.isProtected; }
  boolean isInternal() { return this.isInternal; }
}
