package com.verba.language.test.statements;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.rvalue.math.RpnExpression;
import com.verba.language.parsing.expressions.rvalue.math.RpnMap;
import com.verba.language.parsing.expressions.rvalue.simple.MathOpExpression;
import com.verba.language.parsing.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.language.test.loader.TestFileLoader;
import org.junit.Test;

/**
 * Created by sircodesalot on 14/9/15.
 */
public class StatementTests {
    @Test
  public void testMathFunction() {
    StaticSpaceExpression statementTests = TestFileLoader.STATEMENT_TESTS;

      SymbolTableEntry mathFunction = statementTests.globalSymbolTable().getByFqn("math_statements").single();
      SymbolTableEntry mathStatements = statementTests.globalSymbolTable().getByFqn("math_statements.simple_addition").single();
      ValDeclarationStatement statement = mathStatements.instanceAs(ValDeclarationStatement.class);

      RpnExpression rpnExpression = (RpnExpression) statement.rvalue();
      RpnMap expressions = rpnExpression.expressions();

      QIterable<VerbaExpression> polishNotation = expressions.getPolishNotation();
      assert(polishNotation.firstAs(NumericExpression.class).asInt() == 5);
      assert(polishNotation.getAs(1, NumericExpression.class).asInt() == 7);
      assert(polishNotation.lastAs(MathOpExpression.class).operator().representation().equals("+"));


      SymbolTableEntry simpleSubtraction = statementTests.globalSymbolTable().getByFqn("math_statements.simple_subtraction").single();
      ValDeclarationStatement subtractionStatement = simpleSubtraction.instanceAs(ValDeclarationStatement.class);
    }
}
