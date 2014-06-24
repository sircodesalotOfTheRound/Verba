package com.verba.language.expressions.blockheader.functions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.blockheader.NamedBlockExpression;
import com.verba.language.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.expressions.categories.GenericExpression;
import com.verba.language.expressions.categories.InvokableExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.expressions.categories.TypedExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.expressions.members.MemberExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;
import com.verba.language.lexing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class FunctionDeclarationExpression extends VerbaExpression
    implements NamedBlockExpression, TypedExpression, InvokableExpression, GenericExpression {

    private final FullyQualifiedNameExpression identifier;
    private final BlockDeclarationExpression block;
    private TypeDeclarationExpression returnType;

    public FunctionDeclarationExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        lexer.readCurrentAndAdvance(KeywordToken.class, "fn");
        this.identifier = FullyQualifiedNameExpression.read(this, lexer);

        if (lexer.currentIs(OperatorToken.class, ":")) {
            lexer.readCurrentAndAdvance(OperatorToken.class, ":");
            this.returnType = TypeDeclarationExpression.read(this, lexer);
        }

        this.block = BlockDeclarationExpression.read(this, lexer);
    }

    public static FunctionDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
        return new FunctionDeclarationExpression(parent, lexer);
    }


    public boolean hasGenericParameters() {
        return this.primaryIdentifier().hasGenericParameters();
    }

    public boolean hasParameters() {
        return (this.primaryIdentifier().hasParameters());
    }

    public MemberExpression primaryIdentifier() {
        return this.declaration().first();
    }

    public FullyQualifiedNameExpression declaration() {
        return this.identifier;
    }

    @Override
    public BlockDeclarationExpression block() {
        return this.block;
    }

    @Override
    public QIterable<TupleDeclarationExpression> parameters() {
        return this.primaryIdentifier().parameterLists();
    }

    public GenericTypeListExpression genericParameters() {
        return this.primaryIdentifier().genericParameterList();
    }

    @Override
    public String name() {
        return this.primaryIdentifier().memberName();
    }

    @Override
    public boolean hasTypeConstraint() {
        return this.returnType != null;
    }

    @Override
    public TypeDeclarationExpression typeDeclaration() {
        return this.returnType;
    }
}
