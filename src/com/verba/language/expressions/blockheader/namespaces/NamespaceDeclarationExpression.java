package com.verba.language.expressions.blockheader.namespaces;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.blockheader.NamedBlockExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class NamespaceDeclarationExpression extends VerbaExpression implements NamedBlockExpression {
    private final FullyQualifiedNameExpression identifier;
    private final BlockDeclarationExpression block;

    public NamespaceDeclarationExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        lexer.readCurrentAndAdvance(KeywordToken.class, "namespace");

        this.identifier = FullyQualifiedNameExpression.read(this, lexer);
        this.block = BlockDeclarationExpression.read(this, lexer);
    }

    public static NamespaceDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
        return new NamespaceDeclarationExpression(parent, lexer);
    }

    public FullyQualifiedNameExpression identifier() {
        return this.identifier;
    }

    public BlockDeclarationExpression block() {
        return this.block;
    }

    @Override
    public String name() {
        return this.identifier.members().first().memberName();
    }
}
