package com.verba.language.lexing.tokens.operators.mathop;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class SubtractOpToken extends MathOpToken {

    public SubtractOpToken() {
        super("-");
    }

    @Override
    public int getPriorityLevel() {
        return 1;
    }
}
