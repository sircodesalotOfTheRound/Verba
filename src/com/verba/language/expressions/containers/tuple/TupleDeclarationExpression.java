package com.verba.language.expressions.containers.tuple;

import com.javalinq.implementations.QList;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.DataContainerExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.EnclosureToken;
import com.verba.language.lexing.tokens.operators.OperatorToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-24.
 */
public class TupleDeclarationExpression extends VerbaExpression implements TypeDeclarationExpression,
    DataContainerExpression {

    QList<VerbaExpression> tokens = new QList<>();

    public TupleDeclarationExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);
        this.tokens = this.readContents(lexer);
    }

    private QList<VerbaExpression> readContents(Lexer lexer) {
        QList<VerbaExpression> contents = new QList<>();

        lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
        while (lexer.notEOF() && !lexer.currentIs(EnclosureToken.class, ")")) {
            contents.add((VerbaExpression) TupleItemExpression.read(this, lexer));

            // If a comma is seen, consume it.
            if (lexer.currentIs(OperatorToken.class, ",")) lexer.readCurrentAndAdvance();
        }
        lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

        return contents;
    }

    public static boolean isTupleTypeDeclaration(Lexer lexer) {
        return lexer.currentIs(EnclosureToken.class, "(");
    }

    public static TupleDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
        return new TupleDeclarationExpression(parent, lexer);
    }

    public String representation() {
        throw new NotImplementedException();
    }

    public boolean hasItems() {
        return this.tokens.any();
    }

    public long count() {
        return this.tokens.count();
    }

    public VerbaExpression get(int index) {
        return this.tokens.get(index);
    }

    public QList<VerbaExpression> items() {
        return this.tokens;
    }
}
