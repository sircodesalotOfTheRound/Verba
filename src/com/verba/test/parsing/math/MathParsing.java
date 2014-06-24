package com.verba.test.parsing.math;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.rvalue.pemdas.RpnRValueExpression;
import com.verba.language.expressions.rvalue.simple.MathOpExpression;
import com.verba.language.expressions.rvalue.simple.NumericExpression;
import com.verba.language.lexing.Lexer;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-4-28.
 */
public class MathParsing {
    @Test
    public void calculateMathExpression() {
        Lexer lexer = GeneralLexing.generateLexerFromString("1 + 1 * 2 + 3");
        RpnRValueExpression mathExpression = (RpnRValueExpression) VerbaExpression.read(null, lexer);

        QIterable<VerbaExpression> verbaExpressions = mathExpression.rpnStack();
        assert (verbaExpressions.getAs(0, NumericExpression.class).asInt() == 1);
        assert (verbaExpressions.getAs(1, NumericExpression.class).asInt() == 1);
        assert (verbaExpressions.getAs(2, NumericExpression.class).asInt() == 2);
        assert (verbaExpressions.getAs(3, MathOpExpression.class).operator().is("*"));
        assert (verbaExpressions.getAs(4, MathOpExpression.class).operator().is("+"));
        assert (verbaExpressions.getAs(5, NumericExpression.class).asInt() == 3);
        assert (verbaExpressions.getAs(6, MathOpExpression.class).operator().is("+"));
    }


}
