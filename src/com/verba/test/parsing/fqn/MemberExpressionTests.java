package com.verba.test.parsing.fqn;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.members.MemberExpression;
import com.verba.language.lexing.Lexer;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-4-25.
 */
public class MemberExpressionTests {
    @Test
    public void simpleMemberExpression() {
        Lexer lexer = GeneralLexing.generateLexerFromString("Name");
        MemberExpression expression = MemberExpression.read(null, lexer);

        assert (!expression.hasGenericParameters());
        assert (!expression.hasParameters());
    }

    @Test
    public void memberExpressionWithEmptyParamter() {
        Lexer lexer = GeneralLexing.generateLexerFromString("Name()");
        MemberExpression expression = MemberExpression.read(null, lexer);

        assert (!expression.hasGenericParameters());
        assert (expression.hasParameters());
    }

    @Test
    public void genericMemberExpression() {
        Lexer lexer = GeneralLexing.generateLexerFromString("Name<String, Another : String>");
        MemberExpression expression = MemberExpression.read(null, lexer);

        assert (expression.hasGenericParameters());
        assert (expression.genericParameterList().parameters().count() == 2);
    }


    @Test
    public void curriedMemberExpression() {
        Lexer lexer = GeneralLexing.generateLexerFromString("Name(first)(second)");
        MemberExpression expression = MemberExpression.read(null, lexer);

        assert (!expression.hasGenericParameters());
        assert (expression.parameterLists().count() == 2);

        // Read the first parameter
        VerbaExpression firstParameter = expression.parameterLists().first().get(0);
        VarNameDeclarationExpression firstVarName = (VarNameDeclarationExpression) firstParameter;

        assert (firstVarName.representation().equals("first"));

        // Read the second parameter
        VerbaExpression secondParameter = expression.parameterLists().get(1).get(0);
        VarNameDeclarationExpression secondVarName = (VarNameDeclarationExpression) secondParameter;

        assert (secondVarName.representation().equals("second"));
    }

    @Test
    public void genericMemberExpressionWithParamters() {
        Lexer lexer = GeneralLexing.generateLexerFromString("Name<String, Another : String>(1, two, third)");
        MemberExpression expression = MemberExpression.read(null, lexer);

        assert (expression.hasGenericParameters());
        assert (expression.genericParameterList().parameters().count() == 2);
        assert (expression.parameterLists().get(0).count() == 3);
    }


}
