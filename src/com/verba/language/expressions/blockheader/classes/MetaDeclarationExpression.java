package com.verba.language.expressions.blockheader.classes;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.blockheader.NamedBlockExpression;
import com.verba.language.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class MetaDeclarationExpression extends VerbaExpression implements NamedBlockExpression {
    private final FullyQualifiedNameExpression identifier;
    private final TupleDeclarationExpression parameterList;
    private final BlockDeclarationExpression block;
    private final GenericTypeListExpression genericTypeList;

    public MetaDeclarationExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        lexer.readCurrentAndAdvance(KeywordToken.class, "meta");

        this.identifier = FullyQualifiedNameExpression.read(this, lexer);
        this.genericTypeList = GenericTypeListExpression.read(this, lexer);
        this.parameterList = TupleDeclarationExpression.read(this, lexer);
        this.block = BlockDeclarationExpression.read(this, lexer);
    }

    public static MetaDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
        return new MetaDeclarationExpression(parent, lexer);
    }

    public FullyQualifiedNameExpression identifier() {
        return this.identifier;
    }

    public TupleDeclarationExpression parameterList() {
        return this.parameterList;
    }

    public BlockDeclarationExpression block() {
        return this.block;
    }

    public GenericTypeListExpression genericParameters() {
        return this.genericTypeList;
    }

    @Override
    public String name() {
        return this.identifier.members().first().memberName();
    }
}
