package com.verba.language.test.typesig;

import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.test.loader.TestFileLoader;
import org.junit.Test;

/**
 * Created by sircodesalot on 14/9/9.
 */
public class InlineClassTests {

  @Test
  public void emptyInlineClassTest() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;
    ClassDeclarationExpression expression = singleFileTest.allSubExpressions()
      .ofType(ClassDeclarationExpression.class)
      .single(declaration -> declaration.name().equals("EmptyInlineClass"));

    assert (expression.isInlineClass());
    assert (!expression.hasBlock());
    assert (!expression.hasTraits());
    assert (!expression.hasGenericParameters());
  }


  @Test
  public void parameterlessInlineClass() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;
    ClassDeclarationExpression expression = singleFileTest.allSubExpressions()
      .ofType(ClassDeclarationExpression.class)
      .single(declaration -> declaration.name().equals("ParameterlessInlineClass"));

    assert (expression.isInlineClass());
    assert (!expression.hasBlock());
    assert (!expression.hasTraits());
    assert (!expression.hasGenericParameters());
  }

  @Test
  public void parameterlessDerivedClass() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;
    ClassDeclarationExpression expression = singleFileTest.allSubExpressions()
      .ofType(ClassDeclarationExpression.class)
      .single(declaration -> declaration.name().equals("ParameterlessDerivedClass"));

    assert (expression.isInlineClass());
    assert (expression.hasTraits());
    assert (expression.traits().singleOrNull(trait -> trait.representation().equals("ParameterlessInlineClass")) != null);
    assert (!expression.hasBlock());
    assert (!expression.hasGenericParameters());
  }
}
