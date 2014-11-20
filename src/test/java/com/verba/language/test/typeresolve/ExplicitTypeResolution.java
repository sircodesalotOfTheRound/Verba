package com.verba.language.test.typeresolve;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.test.loader.TestFileLoader;
import org.junit.Test;

/**
 * Created by sircodesalot on 14/9/12.
 */
public class ExplicitTypeResolution {
  @Test
  public void testExplicitUnitFunction() {
    StaticSpaceExpression codeFile = TestFileLoader.TYPE_RESOLUTION_TESTS;

    FunctionDeclarationExpression explicitUnitFunction = codeFile.allSubExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("explicitUnitFunction"));

    assert (explicitUnitFunction.hasTypeConstraint());
    assert (explicitUnitFunction.typeDeclaration().representation().equals("unit"));
  }

  @Test
  public void testExplicitParameterFunction() {
    StaticSpaceExpression codeFile = TestFileLoader.TYPE_RESOLUTION_TESTS;

    FunctionDeclarationExpression explicitParameterFunction = codeFile.allSubExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("explicitParameterFunction"));

    QIterable<NamedValueExpression> parameters = explicitParameterFunction
      .parameterSets()
      .first()
      .items()
      .cast(NamedValueExpression.class);

    assert (parameters.first().name().equals("first"));
    assert (parameters.first().hasTypeConstraint());
    assert (parameters.first().typeDeclaration().representation().equals("string"));

    assert (parameters.last().name().equals("second"));
    assert (parameters.last().hasTypeConstraint());
    assert (parameters.last().typeDeclaration().representation().equals("uint32"));
  }

  @Test
  public void testExplicitVariables() {
    StaticSpaceExpression codeFile = TestFileLoader.TYPE_RESOLUTION_TESTS;

    FunctionDeclarationExpression explicitVariables = codeFile.allSubExpressions()
      .ofType(FunctionDeclarationExpression.class)
      .singleOrNull(function -> function.name().equals("explicitVariables"));

    ValDeclarationStatement first = explicitVariables.block().firstAs(ValDeclarationStatement.class);
    ValDeclarationStatement second = explicitVariables.block().lastAs(ValDeclarationStatement.class);

    assert(first.name().equals("first"));
    assert(first.hasTypeConstraint());
    assert(first.typeDeclaration().representation().equals("string"));

    assert(second.name().equals("second"));
    assert(second.hasTypeConstraint());
    assert(second.typeDeclaration().representation().equals("uint32"));
  }

}
