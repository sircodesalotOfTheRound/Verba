package com.verba.language.validation.violations;

import com.verba.language.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14-5-3.
 */
public class ValidationViolation {
    private final String message;
    private final VerbaExpression expression;

    public ValidationViolation(VerbaExpression expression, String format, Object... args) {
        this.expression = expression;
        this.message = String.format(format, args);
    }

    public String message() {
        return this.message;
    }

    public int line() {
        return this.expression.lexInfo().line();
    }

    public int column() {
        return this.expression.lexInfo().column();
    }

    public String filename() {
        return this.expression.lexInfo().filename();
    }


    @Override
    public String toString() {
        return String.format("%s (%s %s:%s)", this.message, this.filename(), this.line(), this.column());
    }
}
