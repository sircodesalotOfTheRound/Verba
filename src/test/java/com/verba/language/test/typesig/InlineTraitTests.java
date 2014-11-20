package com.verba.language.test.typesig;

import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.blockheader.classes.TraitDeclarationExpression;
import com.verba.language.test.loader.TestFileLoader;
import org.junit.Test;

/**
 * Created by sircodesalot on 14/9/9.
 */
public class InlineTraitTests {

  @Test
  public void emptyInlineTraitTest() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;
    TraitDeclarationExpression expression = singleFileTest.allSubExpressions()
      .ofType(TraitDeclarationExpression.class)
      .single(declaration -> declaration.name().equals("EmptyInlineTrait"));

    assert (expression.isInlineTrait());
    assert (!expression.hasBlock());
    assert (!expression.hasTraits());
    assert (!expression.hasGenericParameters());
  }

  @Test
  public void parameterlessInlineTrait() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;
    TraitDeclarationExpression expression = singleFileTest.allSubExpressions()
      .ofType(TraitDeclarationExpression.class)
      .single(declaration -> declaration.name().equals("ParameterlessInlineTrait"));

    assert (expression.isInlineTrait());
    assert (!expression.hasBlock());
    assert (!expression.hasTraits());
    assert (!expression.hasGenericParameters());
  }

  @Test
  public void parameterlessDerivedTrait() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;
    TraitDeclarationExpression expression = singleFileTest.allSubExpressions()
      .ofType(TraitDeclarationExpression.class)
      .single(declaration -> declaration.name().equals("ParameterlessDerivedTrait"));

    assert (expression.isInlineTrait());
    assert (expression.hasTraits());
    assert (expression.traits().singleOrNull(trait -> trait.representation().equals("ParameterlessInlineTrait")) != null);
    assert (!expression.hasBlock());
    assert (!expression.hasGenericParameters());
  }
}
