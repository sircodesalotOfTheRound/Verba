package com.verba.language.expressions.categories;

import com.javalinq.interfaces.QIterable;
import com.verba.language.exceptions.ParseException;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.expressions.containers.json.JsonExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.EnclosureToken;

/**
 * Represents some sort of non-polymorphic container object (Array, Tuple, Json, Set).
 */
public interface DataContainerExpression {

    public QIterable<VerbaExpression> items();

    public static DataContainerExpression read(VerbaExpression parent, Lexer lexer) {
        if (lexer.currentIs(EnclosureToken.class, "[")) return ArrayDeclarationExpression.read(parent, lexer);
        else if (lexer.currentIs(EnclosureToken.class, "(")) return TupleDeclarationExpression.read(parent, lexer);
        else if (lexer.currentIs(EnclosureToken.class, "{")) return JsonExpression.read(parent, lexer);

        throw new ParseException("Expected container parsing.");
    }
}
