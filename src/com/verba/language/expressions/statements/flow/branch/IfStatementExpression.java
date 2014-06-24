package com.verba.language.expressions.statements.flow.branch;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.block.BlockDeclarationExpression;
import com.verba.language.expressions.categories.RValueExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.EnclosureToken;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class IfStatementExpression extends VerbaExpression {
    private RValueExpression testExpression;
    private BlockDeclarationExpression block;

    public IfStatementExpression(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        lexer.readCurrentAndAdvance(KeywordToken.class, "if");
        lexer.readCurrentAndAdvance(EnclosureToken.class, "(");
        this.testExpression = RValueExpression.read(this, lexer);
        lexer.readCurrentAndAdvance(EnclosureToken.class, ")");

        this.block = BlockDeclarationExpression.read(this, lexer);
    }

    public static IfStatementExpression read(VerbaExpression parent, Lexer lexer) {
        return new IfStatementExpression(parent, lexer);
    }

    public RValueExpression testExpression() {
        return this.testExpression;
    }

    public BlockDeclarationExpression block() {
        return this.block;
    }

}
