package com.verba.test.parsing.fqn;

import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.expressions.members.MemberExpression;
import com.verba.language.expressions.rvalue.lambda.LambdaExpression;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.lexing.Lexer;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-4-25.
 */
public class FQNTests {

    @Test
    public void fullyQualifiedNameTest() {
        Lexer lexer = GeneralLexing.generateLexerFromString("First<T>(1).Second<String, Another : String>(1, two, x -> x)");
        FullyQualifiedNameExpression expression = FullyQualifiedNameExpression.read(null, lexer);

        assert (expression.count() == 2); // Has two MemberExpressions

        MemberExpression first = expression.first();

        assert (first.genericParameterList().count() == 1);
        assert (first.parameterLists().count() == 1);

        // Verify the generic Paramters
        /* TODO: Reimplement
        assert (first.genericParameterList()
            .map(VarNameDeclarationExpression::representation)
            .matchesSequence("T"));
        */

        // Verify the actual parameters
        assert (first.parameterLists().first().items().firstAs(NumericExpression.class).asInt() == 1);

        MemberExpression second = expression.get(1);
        assert (second.genericParameterList().count() == 2);
        assert (second.parameterLists().count() == 1);

        // Verify the generic Paramters
        /* TODO: Reimplement
        assert (second.genericParameterList()
            .map(VarNameDeclarationExpression::representation)
            .matchesSequence("String", "Another : String"));
        */

        // Verify the actual parameters
        TupleDeclarationExpression secondMemberParameters = second.parameterLists().first();

        assert (secondMemberParameters.count() == 3);
        assert (secondMemberParameters.items().firstAs(NumericExpression.class).asInt() == 1);
        assert ((VarNameDeclarationExpression) secondMemberParameters.get(1)).representation().equals("two");
        assert (secondMemberParameters.get(2) instanceof LambdaExpression);
    }
}

