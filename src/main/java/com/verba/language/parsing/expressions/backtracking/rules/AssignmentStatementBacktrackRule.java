package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.info.TokenSignature;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.identifiers.IdentifierToken;
import com.verba.language.parsing.tokens.operators.assignment.AssignmentToken;
import com.verba.language.parsing.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-27.
 */
public class AssignmentStatementBacktrackRule extends BacktrackRule {
  private static TokenSignature[] tupleAssignmentSequence
    = new TokenSignature[]{
    TokenSignature.make(EnclosureToken.class, "("),
    TokenSignature.make(IdentifierToken.class)
  };

  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    // Assignments should start with an LValue
    if (!restOfLine.startsWith(IdentifierToken.class)
      || !restOfLine.startsWithSequence(tupleAssignmentSequence))

      return false;

    // Should contain assignment operator
    if (!restOfLine.contains(AssignmentToken.class)) return false;

    // If these preliminary tests check out, go ahead and try.
    return true;
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
      return AssignmentStatementExpression.read(parent, lexer);
  }
}
