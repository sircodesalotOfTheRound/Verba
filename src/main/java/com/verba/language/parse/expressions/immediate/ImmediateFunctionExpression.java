package com.verba.language.parse.expressions.immediate;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.language.parse.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 15/2/26.
 */
public class ImmediateFunctionExpression extends VerbaExpression {
  private final VerbaExpression declaration;
  private final FunctionDeclarationExpression function;
  private final TupleDeclarationExpression arguments;

  public ImmediateFunctionExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.declaration = readDeclaration(lexer);
    this.arguments = readArgumentsList(lexer);
    this.function = determineFunction(this.declaration);
  }

  private FunctionDeclarationExpression determineFunction(VerbaExpression declaration) {
    if (this.declaration instanceof FunctionDeclarationExpression) {
      return (FunctionDeclarationExpression) this.declaration;
    } else if (this.declaration instanceof DeclarationModifierExrpression) {
      return (FunctionDeclarationExpression) ((DeclarationModifierExrpression) this.declaration).modifiedExpression();
    } else {
      return null;
    }
  }


  private TupleDeclarationExpression readArgumentsList(Lexer lexer) {
    if (lexer.currentIs(EnclosureToken.class, EnclosureToken.OPEN_PARENS)) {
      return TupleDeclarationExpression.read(this, lexer);
    } else {
      return null;
    }
  }

  private VerbaExpression readDeclaration(Lexer lexer) {
    lexer.readCurrentAndAdvance(EnclosureToken.class, EnclosureToken.OPEN_PARENS);
    VerbaExpression delcaration = VerbaExpression.read(this, lexer);
    lexer.readCurrentAndAdvance(EnclosureToken.class, EnclosureToken.CLOSE_PARENS);

    return delcaration;
  }

  public boolean isModified() { return this.declaration instanceof DeclarationModifierExrpression; }
  public DeclarationModifierExrpression modifierExpression() { return (DeclarationModifierExrpression) this.declaration; }
  public FunctionDeclarationExpression function() {  return this.function; }
  public TupleDeclarationExpression arguments() { return this.arguments; }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  public boolean isAsynchronous() {
    if (this.isModified()) {
      return this.modifierExpression().modifier().is(KeywordToken.class, KeywordToken.ASYNC);
    } else {
      return false;
    }
  }

  public static ImmediateFunctionExpression read(VerbaExpression parent, Lexer lexer) {
    return new ImmediateFunctionExpression(parent, lexer);
  }
}
