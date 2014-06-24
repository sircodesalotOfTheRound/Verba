package com.verba.language.lexing.tokens.operators.mathop;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class ModuloOpToken extends MathOpToken {

    public ModuloOpToken() {
        super("%");
    }

    @Override
    public int getPriorityLevel() {
        return 2;
    }
}
