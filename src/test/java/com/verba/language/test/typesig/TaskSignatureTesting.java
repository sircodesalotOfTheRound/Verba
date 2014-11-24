package com.verba.language.test.typesig;

import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.functions.TaskDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.test.loader.TestFileLoader;
import org.junit.Test;

/**
 * Created by sircodesalot on 14/9/9.
 */
public class TaskSignatureTesting {
  @Test
  public void testEmptyTask() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    TaskDeclarationExpression emptyTask = singleFileTest.allExpressions()
      .ofType(TaskDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("empty_task"));

    assert (emptyTask != null);
    assert (!emptyTask.block().expressions().any());
    assert (!emptyTask.hasGenericParameters());
    assert (emptyTask.parameterSets().count() == 1);
    assert (!emptyTask.parameterSets().single().hasItems());
  }

  @Test
  public void testSingleDynamicArgumentTask() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    TaskDeclarationExpression singleDynamicArgumentTask = singleFileTest.allExpressions()
      .ofType(TaskDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("single_dynamic_argument_task"));

    assert (singleDynamicArgumentTask != null);
    assert (!singleDynamicArgumentTask.block().expressions().any());
    assert (!singleDynamicArgumentTask.hasGenericParameters());
    assert (singleDynamicArgumentTask.parameterSets().count() == 1);
    assert (singleDynamicArgumentTask.parameterSets().single().items().singleOrNull() != null);

    VerbaExpression arg = singleDynamicArgumentTask.parameterSets().single().items().single();
    NamedValueExpression dynamicArgument = (NamedValueExpression) arg;

    assert (dynamicArgument.name().equals("dynamic_argument"));
    assert (!dynamicArgument.hasTypeConstraint());
  }

  @Test
  public void testSingleStaticTypedArgumentTask() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    TaskDeclarationExpression singleStaticTypedArgumentTask = singleFileTest.allExpressions()
      .ofType(TaskDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("single_static_typed_argument_task"));

    assert (singleStaticTypedArgumentTask != null);
    assert (!singleStaticTypedArgumentTask.block().expressions().any());
    assert (!singleStaticTypedArgumentTask.hasGenericParameters());
    assert (singleStaticTypedArgumentTask.parameterSets().count() == 1);
    assert (singleStaticTypedArgumentTask.parameterSets().single().items().singleOrNull() != null);

    VerbaExpression arg = singleStaticTypedArgumentTask.parameterSets().single().items().single();
    NamedValueExpression dynamicArgument = (NamedValueExpression) arg;

    assert (dynamicArgument.name().equals("string_argument"));
    assert (dynamicArgument.hasTypeConstraint());
    assert (dynamicArgument.typeDeclaration().representation().equals("string"));
  }


  @Test
  public void testMixedArgumentTask() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    TaskDeclarationExpression mixedArgumentTask = singleFileTest.allExpressions()
      .ofType(TaskDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("mixed_argument_task"));

    assert (mixedArgumentTask != null);
    assert (!mixedArgumentTask.block().expressions().any());
    assert (!mixedArgumentTask.hasGenericParameters());
    assert (mixedArgumentTask.parameterSets().count() == 1);
    assert (mixedArgumentTask.parameterSets().single().items().count() == 3);

    NamedValueExpression stringArg = mixedArgumentTask
      .parameterSets()
      .single()
      .items()
      .firstAs(NamedValueExpression.class);

    assert (stringArg.name().equals("string_arg"));
    assert (stringArg.hasTypeConstraint());
    assert (stringArg.typeDeclaration().representation().equals("string"));

    NamedValueExpression intArg = (NamedValueExpression) mixedArgumentTask
      .parameterSets()
      .single()
      .items()
      .get(1);

    assert (intArg.name().equals("int_arg"));
    assert (intArg.hasTypeConstraint());
    assert (intArg.typeDeclaration().representation().equals("uint64"));

    NamedValueExpression dynamicArg = mixedArgumentTask
      .parameterSets()
      .single()
      .items()
      .lastAs(NamedValueExpression.class);

    assert (dynamicArg.name().equals("dynamic_arg"));
    assert (!dynamicArg.hasTypeConstraint());
  }

  @Test
  public void testExplicitReturnTypeTask() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    TaskDeclarationExpression explicitReturnTypeTask = singleFileTest.allExpressions()
      .ofType(TaskDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("explicit_return_type_task"));

    assert (explicitReturnTypeTask != null);
    assert (explicitReturnTypeTask.hasTypeConstraint());
    assert (explicitReturnTypeTask.typeDeclaration().representation().equals("uint64"));
    assert (!explicitReturnTypeTask.block().expressions().any());
    assert (!explicitReturnTypeTask.hasGenericParameters());
    assert (explicitReturnTypeTask.parameterSets().count() == 1);
    assert (!explicitReturnTypeTask.parameterSets().single().hasItems());
  }

  @Test
  public void testGenericTask() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    TaskDeclarationExpression genericTask = singleFileTest.allExpressions()
      .ofType(TaskDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("generic_task"));

    assert (genericTask != null);
    assert (genericTask.hasGenericParameters());
    assert (genericTask.genericParameters().first().representation().equals("T"));
    assert (genericTask.genericParameters().last().hasTypeConstraint());
    assert (genericTask.genericParameters().last().identifier().representation().equals("U"));
    assert (genericTask.genericParameters().last().typeDeclaration().representation().equals("object"));
    assert (!genericTask.block().expressions().any());
    assert (genericTask.parameterSets().count() == 1);
    assert (!genericTask.parameterSets().single().hasItems());
  }
}
