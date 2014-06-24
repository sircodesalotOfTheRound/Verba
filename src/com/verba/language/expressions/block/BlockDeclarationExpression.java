package com.verba.language.expressions.block;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.exceptions.ParseException;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.SymbolTableExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.EnclosureToken;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-2-18.
 */
public class BlockDeclarationExpression extends VerbaExpression
    implements QIterable<VerbaExpression>, SymbolTableExpression {

    private final QList<VerbaExpression> expressions = new QList<>();

    private BlockDeclarationExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        // If the first token isn't an open brace, then just return empty
        if (!lexer.currentIs(EnclosureToken.class, EnclosureToken.OPEN_BRACE)) {
            return;
        }

        lexer.readCurrentAndAdvance(EnclosureToken.class, EnclosureToken.OPEN_BRACE);

        while (lexer.notEOF() && !lexer.currentIs(EnclosureToken.class, "}")) {
            VerbaExpression result = VerbaExpression.read(this, lexer);

            if (result != null) this.expressions.add(result);
            else throw new ParseException("Expected statement.");
        }

        lexer.readCurrentAndAdvance(EnclosureToken.class, EnclosureToken.CLOSE_BRACE);
    }

    public boolean hasItems() {
        return this.expressions.any();
    }

    public QIterable<VerbaExpression> expressions() {
        return this.expressions;
    }

    public static BlockDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
        return new BlockDeclarationExpression(parent, lexer);
    }

    @Override
    public Iterator<VerbaExpression> iterator() {
        return this.expressions.iterator();
    }
}
