package com.verba.language.parsing.expressions.categories;


import com.javalinq.interfaces.QIterable;

/**
 * Implemented on ClassDeclarationExpression and TraitDeclaration expression.
 * Means that this class can derive from other classes / traits.
 */
public interface PolymorphicExpression {
  public QIterable<TypeDeclarationExpression> traits();

  public boolean hasTraits();
}
