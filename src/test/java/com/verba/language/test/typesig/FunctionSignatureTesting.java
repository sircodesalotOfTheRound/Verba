package com.verba.language.test.typesig;

import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.test.loader.TestFileLoader;
import org.junit.Test;

/**
 * Created by sircodesalot on 14/9/9.
 */
public class FunctionSignatureTesting {
  @Test
  public void testEmptyFunction() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    FunctionDeclarationExpression emptyFunction = singleFileTest.allExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("empty_function"));

    assert (emptyFunction != null);
    assert (!emptyFunction.block().expressions().any());
    assert (!emptyFunction.hasGenericParameters());
    assert (emptyFunction.parameterSets().count() == 1);
    assert (!emptyFunction.parameterSets().single().hasItems());
  }

  @Test
  public void testSingleDynamicArgumentFunction() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    FunctionDeclarationExpression singleDynamicArgumentFunction = singleFileTest.allExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("single_dynamic_argument_function"));

    assert (singleDynamicArgumentFunction != null);
    assert (!singleDynamicArgumentFunction.block().expressions().any());
    assert (!singleDynamicArgumentFunction.hasGenericParameters());
    assert (singleDynamicArgumentFunction.parameterSets().count() == 1);
    assert (singleDynamicArgumentFunction.parameterSets().single().items().singleOrNull() != null);

    VerbaExpression arg = singleDynamicArgumentFunction.parameterSets().single().items().single();
    NamedValueExpression dynamicArgument = (NamedValueExpression) arg;

    assert (dynamicArgument.name().equals("dynamic_argument"));
    assert (!dynamicArgument.hasTypeConstraint());
  }

  @Test
  public void testSingleStaticTypedArgumentFunction() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    FunctionDeclarationExpression singleStaticTypedArgumentFunction = singleFileTest.allExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("single_static_typed_argument_function"));

    assert (singleStaticTypedArgumentFunction != null);
    assert (!singleStaticTypedArgumentFunction.block().expressions().any());
    assert (!singleStaticTypedArgumentFunction.hasGenericParameters());
    assert (singleStaticTypedArgumentFunction.parameterSets().count() == 1);
    assert (singleStaticTypedArgumentFunction.parameterSets().single().items().singleOrNull() != null);

    VerbaExpression arg = singleStaticTypedArgumentFunction.parameterSets().single().items().single();
    NamedValueExpression dynamicArgument = (NamedValueExpression) arg;

    assert (dynamicArgument.name().equals("string_argument"));
    assert (dynamicArgument.hasTypeConstraint());
    assert (dynamicArgument.typeDeclaration().representation().equals("string"));
  }


  @Test
  public void testMixedArgumentFunction() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    FunctionDeclarationExpression mixedArgumentFunction = singleFileTest.allExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("mixed_argument_function"));

    assert (mixedArgumentFunction != null);
    assert (!mixedArgumentFunction.block().expressions().any());
    assert (!mixedArgumentFunction.hasGenericParameters());
    assert (mixedArgumentFunction.parameterSets().count() == 1);
    assert (mixedArgumentFunction.parameterSets().single().items().count() == 3);

    NamedValueExpression stringArg = mixedArgumentFunction
      .parameterSets()
      .single()
      .items()
      .firstAs(NamedValueExpression.class);

    assert (stringArg.name().equals("string_arg"));
    assert (stringArg.hasTypeConstraint());
    assert (stringArg.typeDeclaration().representation().equals("string"));

    NamedValueExpression intArg = (NamedValueExpression) mixedArgumentFunction
      .parameterSets()
      .single()
      .items()
      .get(1);

    assert (intArg.name().equals("int_arg"));
    assert (intArg.hasTypeConstraint());
    assert (intArg.typeDeclaration().representation().equals("uint64"));

    NamedValueExpression dynamicArg = mixedArgumentFunction
      .parameterSets()
      .single()
      .items()
      .lastAs(NamedValueExpression.class);

    assert (dynamicArg.name().equals("dynamic_arg"));
    assert (!dynamicArg.hasTypeConstraint());
  }

  @Test
  public void testExplicitReturnTypeFunction() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    FunctionDeclarationExpression explicitReturnTypeFunction = singleFileTest.allExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("explicit_return_type_function"));

    assert (explicitReturnTypeFunction != null);
    assert (explicitReturnTypeFunction.hasTypeConstraint());
    assert (explicitReturnTypeFunction.typeDeclaration().representation().equals("uint64"));
    assert (!explicitReturnTypeFunction.block().expressions().any());
    assert (!explicitReturnTypeFunction.hasGenericParameters());
    assert (explicitReturnTypeFunction.parameterSets().count() == 1);
    assert (!explicitReturnTypeFunction.parameterSets().single().hasItems());
  }

  @Test
  public void testGenericFunction() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    FunctionDeclarationExpression genericFunction = singleFileTest.allExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("generic_function"));

    assert (genericFunction != null);
    assert (genericFunction.hasGenericParameters());
    assert (genericFunction.genericParameters().first().representation().equals("T"));
    assert (genericFunction.genericParameters().last().hasTypeConstraint());
    assert (genericFunction.genericParameters().last().identifier().representation().equals("U"));
    assert (genericFunction.genericParameters().last().typeDeclaration().representation().equals("string"));
    assert (!genericFunction.block().expressions().any());
    assert (genericFunction.parameterSets().count() == 1);
    assert (!genericFunction.parameterSets().single().hasItems());
  }

  @Test
  public void testTestSignature() {
    StaticSpaceExpression singleFileTest = TestFileLoader.TYPE_SIGNATURE_TESTS;

    SymbolTableEntry signatureEntry = singleFileTest.globalSymbolTable().getEntryListByFqn("test_signature").single();
    SignatureDeclarationExpression signature = signatureEntry.instanceAs(SignatureDeclarationExpression.class);

    assert (signature != null);
    assert (!signature.hasGenericParameters());
    TupleDeclarationExpression parameters = signature.parameterSets().single();
    NamedValueExpression first = parameters.items().firstAs(NamedValueExpression.class);
    NamedValueExpression second = parameters.items().lastAs(NamedValueExpression.class);

    assert(first.name().equals("lhs"));
    assert(first.hasTypeConstraint());
    assert(first.typeDeclaration().representation().equals("string"));

    assert(second.name().equals("rhs"));
    assert(second.hasTypeConstraint());
    assert(second.typeDeclaration().representation().equals("object"));
  }
}
