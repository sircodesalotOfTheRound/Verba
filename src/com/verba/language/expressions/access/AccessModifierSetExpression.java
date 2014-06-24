package com.verba.language.expressions.access;

import com.javalinq.implementations.QList;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.lexing.Lexer;

/**
 * Created by sircodesalot on 14-5-20.
 */
public class AccessModifierSetExpression extends VerbaExpression {
    private final QList<AccessModifierExpression> expressions = new QList<>();

    public AccessModifierSetExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        while (lexer.notEOF() && AccessModifierExpression.ACCESS_MODIFIERS.contains(lexer.current().representation())) {
            AccessModifierExpression accessModifier = AccessModifierExpression.read(this, lexer);
            this.expressions.add(accessModifier);
        }
    }

    public static AccessModifierSetExpression read(VerbaExpression parent, Lexer lexer) {
        return new AccessModifierSetExpression(parent, lexer);
    }
}
