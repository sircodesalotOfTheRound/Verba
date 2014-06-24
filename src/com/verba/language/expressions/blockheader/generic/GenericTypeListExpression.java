package com.verba.language.expressions.blockheader.generic;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.operators.OperatorToken;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class GenericTypeListExpression extends VerbaExpression
    implements QIterable<VarNameDeclarationExpression> {
    QList<VarNameDeclarationExpression> declarations = new QList<>();

    public GenericTypeListExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        if (lexer.notEOF() && lexer.currentIs(OperatorToken.class, "<")) {
            this.readExpressions(lexer);
        }
    }

    private void readExpressions(Lexer lexer) {
        lexer.readCurrentAndAdvance(OperatorToken.class, "<");

        while (!lexer.currentIs(OperatorToken.class, ">")) {
            declarations.add(VarNameDeclarationExpression.read(this, lexer));

            if (!lexer.currentIs(OperatorToken.class, ",")) break;
            else lexer.readCurrentAndAdvance(OperatorToken.class, ",");
        }

        lexer.readCurrentAndAdvance(OperatorToken.class, ">");
    }

    public static GenericTypeListExpression read(VerbaExpression parent, Lexer lexer) {
        return new GenericTypeListExpression(parent, lexer);
    }

    public boolean hasItems() {
        return this.declarations.any();
    }

    public String representation() {
        Iterable<String> items = this.declarations.map(VarNameDeclarationExpression::representation);
        String joinedItems = String.join(", ", items);

        return String.format("<%s>", joinedItems);
    }

    public QList<VarNameDeclarationExpression> parameters() {
        return this.declarations;
    }

    @Override
    public Iterator<VarNameDeclarationExpression> iterator() {
        return this.declarations.iterator();
    }
}
