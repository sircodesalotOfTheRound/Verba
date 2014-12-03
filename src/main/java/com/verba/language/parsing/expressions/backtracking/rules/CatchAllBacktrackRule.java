package com.verba.language.parsing.expressions.backtracking.rules;

import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.backtracking.BacktrackRule;
import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.lexing.Lexer;

/**
 * Created by sircodesalot on 14/11/21.
 */
public class CatchAllBacktrackRule extends BacktrackRule {
  public class CatchAllExpression extends VerbaExpression {
    private final LexInfo lexInfo;

    public CatchAllExpression(VerbaExpression parent, Lexer lexer) {
      super(parent, lexer);
      this.lexInfo = lexer.readCurrentAndAdvance();
    }

    public LexInfo lexInfo() { return this.lexInfo; }

    @Override
    public void accept(SyntaxGraphVisitor visitor) { }
  }

  @Override
  public boolean attemptIf(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return true;
  }

  @Override
  public VerbaExpression attempt(VerbaExpression parent, Lexer lexer, LexList restOfLine) {
    return new CatchAllExpression(parent, lexer);
  }
}
