package com.verba.language.expressions.categories;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.expressions.backtracking.rules.*;
import com.verba.language.expressions.containers.markup.MarkupDeclarationExpressionBacktrackRule;
import com.verba.language.expressions.containers.tuple.TupleItemExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokenization.Token;

/**
 * Created by sircodesalot on 14-2-19.
 */
public interface RValueExpression extends TupleItemExpression, Token {
    // LambdaExpression must come before CastedRValueExpression
    // CastedRValueExpression must come before ContainerExpression!
    final static BacktrackRuleSet<RValueExpression> ruleset
        = new BacktrackRuleSet<RValueExpression>()
        .addRule(new MarkupDeclarationExpressionBacktrackRule())
        .addRule(new RpnBacktrackRule())
        .addRule(new LiteralExpressionRule())
        .addRule(new SetDeclarationExpressionBacktrackRule())
        .addRule(new NewExpressionBacktrackRule())
        .addRule(new LambdaExpressionBacktrackRule())
        .addRule(new CastedRValueExpressionBacktrackRule())
        .addRule(new VarNameExpressionBacktrackRule());

    public static RValueExpression read(VerbaExpression parent, Lexer lexer) {
        return ruleset.resolve(parent, lexer);
    }
}
