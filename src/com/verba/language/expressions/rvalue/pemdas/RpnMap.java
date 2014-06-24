package com.verba.language.expressions.rvalue.pemdas;

import com.javalinq.implementations.QList;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.expressions.backtracking.rules.*;
import com.verba.language.expressions.categories.RValueExpression;
import com.verba.language.expressions.rvalue.simple.MathOpExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexInfo;
import com.verba.language.lexing.tokens.operators.mathop.MathOpToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class RpnMap {
    private static BacktrackRuleSet<RValueExpression> ruleset
        = new BacktrackRuleSet<RValueExpression>()
        .addRule(new LiteralExpressionRule())
        .addRule(new LambdaExpressionBacktrackRule())
        .addRule(new NewExpressionBacktrackRule())
        .addRule(new CastedRValueExpressionBacktrackRule())
        .addRule(new RValueContainerExpressionBacktrackRule());

    private final RpnRValueStack stack = new RpnRValueStack();
    private QList<VerbaExpression> polishNotation = new QList<>();
    private final VerbaExpression parent;
    private final Lexer lexer;

    public RpnMap(VerbaExpression parent, Lexer lexer) {
        this.parent = parent;
        this.lexer = lexer;
    }

    public void project() {
        int startLine = lexer.getCurrentLine();

        while (lexer.notEOF() && lexer.getCurrentLine() == startLine) {
            // If the next item is not a math op, then try to
            // resolve it as an RValue, else break.
            if (!lexer.currentIs(MathOpToken.class)) {
                VerbaExpression expression = (VerbaExpression) ruleset.resolve(parent, lexer);
                if (expression == null) break;
                this.polishNotation.add(expression);

            } else if (lexer.currentIs(MathOpToken.class)) {
                this.handleMathOpToken(lexer);
            }
        }

        this.unravelStack();
    }

    private void handleMathOpToken(Lexer lexer) {
        MathOpExpression mathop = MathOpExpression.read(parent, lexer);
        int currentOpPriorityLevel = getPriorityLevel(mathop);

        if (!stack.hasItems()) stack.push(mathop);
        else {
            int topOfStackOpPriorityLevel = this.getPriorityLevel(stack.peek());
            while (topOfStackOpPriorityLevel >= currentOpPriorityLevel) {
                this.popOperationToOutput();

                if (!stack.hasItems()) break;
                else topOfStackOpPriorityLevel = this.getPriorityLevel(stack.peek());
            }

            stack.push(mathop);
        }
    }

    private void popOperationToOutput() {
        VerbaExpression operation = this.stack.pop();
        this.polishNotation.add(operation);
    }

    private int getPriorityLevel(MathOpExpression mathop) {
        LexInfo lexInfo = mathop.operator();
        MathOpToken mathOpToken = (MathOpToken) lexInfo.getToken();
        return mathOpToken.getPriorityLevel();
    }

    private void unravelStack() {
        while (stack.hasItems()) {
            polishNotation.add(stack.pop());
        }
    }

    public QList<VerbaExpression> getPolishNotation() {
        return this.polishNotation;
    }
}
