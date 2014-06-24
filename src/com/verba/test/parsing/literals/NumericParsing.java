package com.verba.test.parsing.literals;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.lexing.Lexer;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-4-25.
 */
public class NumericParsing {
    @Test
    public void integerParsing() {
        Lexer lexer = GeneralLexing.generateLexerFromString("12345");
        NumericExpression expression = (NumericExpression) VerbaExpression.read(null, lexer);

        assert (expression.asInt() == 12345);
        assert (expression.isPositive());
        assert (!expression.isDecimal());
        assert (expression.size() == NumericExpression.Size.SHORT);
    }

    @Test
    public void decimalParsing() {
        Lexer lexer = GeneralLexing.generateLexerFromString("12345.456");
        NumericExpression expression = (NumericExpression) VerbaExpression.read(null, lexer);

        assert (expression.isDecimal());
        assert (expression.asDecimal() == 12345.456);
    }
}
