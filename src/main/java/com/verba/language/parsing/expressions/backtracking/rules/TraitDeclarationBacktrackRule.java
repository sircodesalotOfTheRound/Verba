package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.backtracking.MismatchException;
import com.verba.language.parsing.expressions.blockheader.classes.TraitDeclarationExpression;
import com.verba.language.parsing.Lexer;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;

/**
 * Created by sircodesalot on 14-2-23.
 */
public class TraitDeclarationBacktrackRule extends BacktrackRule {
  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return restOfLine.startsWith(KeywordToken.class, "trait");
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) throws MismatchException {
    if (restOfLine.startsWith(KeywordToken.class, "trait")) return TraitDeclarationExpression.read(parent, lexer);

    throw MismatchException.getInstance();
  }
}
