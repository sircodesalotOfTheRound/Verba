package com.verba.language.validation;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.validation.violations.ValidationViolation;

/**
 * Created by sircodesalot on 14-5-3.
 */
public abstract class ExpressionValidator<T> {
    private final T target;
    private final QList<ValidationViolation> violations = new QList<>();

    public ExpressionValidator(T target) {
        this.target = target;
    }

    protected void addViolations(Iterable<ValidationViolation> violations) {
        for (ValidationViolation violation : violations) {
            this.addViolation(violation);
        }
    }

    protected void addViolation(ValidationViolation violation) {
        this.violations.add(violation);
    }

    protected void addViolation(VerbaExpression expression, String format, Object... args) {
        this.violations.add(new ValidationViolation(expression, format, args));
    }

    public QIterable<ValidationViolation> violations() {
        return this.violations;
    }

    protected T target() {
        return this.target;
    }
}
