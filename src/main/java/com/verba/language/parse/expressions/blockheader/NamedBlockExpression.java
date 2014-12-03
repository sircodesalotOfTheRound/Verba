package com.verba.language.parse.expressions.blockheader;

import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.parse.expressions.backtracking.rules.*;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.categories.NamedExpression;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.lexing.Lexer;

/**
 * Created by sircodesalot on 14-2-27.
 */
public interface NamedBlockExpression extends SymbolTableExpression, NamedExpression {
  public static BacktrackRuleSet<NamedBlockExpression> declarations
    = new BacktrackRuleSet<NamedBlockExpression>()
    .addRule(new NamespaceDeclarationBacktrackRule())
    .addRule(new PolymorphicExpressionBacktrackRule())
    .addRule(new FunctionDeclarationBacktrackRule())
    .addRule(new InjectedClassDeclarationBacktrackRule())
    .addRule(new MetaDeclarationBacktrackRule())
    .addRule(new ExtendDeclarationBacktrackRule())
    .addRule(new SignatureDeclarationBacktrackRule());

  BlockDeclarationExpression block();

  public static NamedBlockExpression read(VerbaExpression parent, Lexer lexer) {
    return declarations.resolve(parent, lexer);
  }
}
