package com.verba.language.expressions.blockheader.classes;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.blockheader.NamedBlockExpression;
import com.verba.language.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.expressions.categories.PolymorphicExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.expressions.members.MemberExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;
import com.verba.language.lexing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class TraitDeclarationExpression extends VerbaExpression
    implements NamedBlockExpression, PolymorphicExpression {

    private final FullyQualifiedNameExpression identifier;
    private final BlockDeclarationExpression block;
    private QIterable<TypeDeclarationExpression> traits;

    public TraitDeclarationExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        lexer.readCurrentAndAdvance(KeywordToken.class, "trait");
        this.identifier = FullyQualifiedNameExpression.read(this, lexer);

        if (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ":")) {
            lexer.readCurrentAndAdvance(OperatorToken.class, ":");

            this.traits = readBaseTypes(lexer);
        }

        this.block = BlockDeclarationExpression.read(this, lexer);
    }

    private QIterable<TypeDeclarationExpression> readBaseTypes(Lexer lexer) {
        QList<TypeDeclarationExpression> baseTypes = new QList<>();

        do {
            baseTypes.add(TypeDeclarationExpression.read(this, lexer));

            // Read a comma if that's the next item, else break.
            if (lexer.notEOF() && lexer.currentIs(OperatorToken.class, ","))
                lexer.readCurrentAndAdvance(OperatorToken.class, ",");
            else
                break;

        } while (lexer.notEOF());

        return baseTypes;
    }

    public static TraitDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
        return new TraitDeclarationExpression(parent, lexer);
    }

    public BlockDeclarationExpression block() {
        return this.block;
    }

    @Override
    public QIterable<TypeDeclarationExpression> traits() {
        return this.traits;
    }

    public FullyQualifiedNameExpression declaration() {
        return this.identifier;
    }

    public MemberExpression primaryIdentifier() {
        return this.declaration().first();
    }

    public QIterable<TupleDeclarationExpression> inlineParameters() {
        return this.primaryIdentifier().parameterLists();
    }

    public GenericTypeListExpression genericParameters() {
        return this.primaryIdentifier().genericParameterList();
    }

    @Override
    public String name() {
        return this.identifier.members().first().memberName();
    }

    @Override
    public boolean hasTraits() {
        return this.traits != null;
    }
}
