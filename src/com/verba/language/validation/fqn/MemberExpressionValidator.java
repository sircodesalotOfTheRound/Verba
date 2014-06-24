package com.verba.language.validation.fqn;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.members.MemberExpression;
import com.verba.language.validation.ExpressionValidator;

/**
 * Created by sircodesalot on 14-5-5.
 */
public class MemberExpressionValidator extends ExpressionValidator<MemberExpression> {
    public MemberExpressionValidator(MemberExpression target) {
        super(target);
    }

    public boolean hasParameters() {
        return this.member().hasParameters();
    }

    public boolean hasGenericParameters() {
        return this.member().hasGenericParameters();
    }

    public boolean allParametersExplicitlyTypes() {
        QIterable<VerbaExpression> parameters = this.member()
            .parameterLists()
            .flatten(parameterList -> parameterList.items());

        boolean allParametersAreVarNameExpressions = parameters
            .all(parameter -> parameter instanceof VarNameDeclarationExpression);

        if (!allParametersAreVarNameExpressions) return false;

        return parameters
            .cast(VarNameDeclarationExpression.class)
            .all(VarNameDeclarationExpression::hasTypeConstraint);
    }

    public QIterable<VerbaExpression> flattenedParameterLists() {
        return this.member().parameterLists()
            .flatten(list -> list.items());
    }

    public MemberExpression member() {
        return super.target();
    }
}
