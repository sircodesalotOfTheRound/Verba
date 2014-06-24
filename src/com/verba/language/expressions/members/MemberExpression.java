package com.verba.language.expressions.members;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.rvalue.simple.IdentifierExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class MemberExpression extends VerbaExpression {
    private final IdentifierExpression identifier;
    private final GenericTypeListExpression genericParameters;
    private final QList<TupleDeclarationExpression> parameterLists = new QList<>();

    public MemberExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        this.identifier = IdentifierExpression.read(this, lexer);
        this.genericParameters = GenericTypeListExpression.read(this, lexer);

        // Read parameters if they exist
        do {
            if (lexer.currentIs(EnclosureToken.class, "(")) {
                parameterLists.add(TupleDeclarationExpression.read(this, lexer));
            }
        } while (lexer.notEOF() && lexer.currentIs(EnclosureToken.class, "("));
    }

    public static MemberExpression read(VerbaExpression parent, Lexer lexer) {
        return new MemberExpression(parent, lexer);
    }

    public String memberName() {
        return this.identifier.representation();
    }

    public String representation() {
        if (genericParameters.hasItems()) {
            return String.format("%s%s",
                this.identifier.representation(),
                this.genericParameters.representation());
        } else {
            return this.identifier.representation();
        }
    }

    public boolean hasParameters() {
        return this.parameterLists.any();
    }

    public IdentifierExpression identifier() {
        return this.identifier;
    }

    public QIterable<TupleDeclarationExpression> parameterLists() {
        return this.parameterLists;
    }

    public GenericTypeListExpression genericParameterList() {
        return this.genericParameters;
    }

    public boolean hasGenericParameters() {
        return this.genericParameters.hasItems();
    }
}
