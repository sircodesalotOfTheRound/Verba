package com.verba.language.graph.visitors;

import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.block.BlockDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.language.parse.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parse.expressions.containers.json.JsonExpression;
import com.verba.language.parse.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.parse.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parse.expressions.modifiers.DeclarationModifierExrpression;
import com.verba.language.parse.expressions.rvalue.newexpression.NewExpression;
import com.verba.language.parse.expressions.rvalue.simple.BooleanExpression;
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.expressions.withns.WithNsExpression;

/**
 * Created by sircodesalot on 14/9/12.
 */
public abstract class ExpressionTreeVisitor {
  public abstract void visit(BlockDeclarationExpression verbaExpressions);

  public abstract void visit(LitFileRootExpression litFileRoot);

  public abstract void visit(NamedValueExpression namedObjectDeclarationExpression);

  public abstract void visit(PolymorphicDeclarationExpression classDeclarationExpression);

  public abstract void visit(FunctionDeclarationExpression functionDeclarationExpression);

  public abstract void visit(ArrayDeclarationExpression arrayDeclarationExpression);

  public abstract void visit(JsonExpression jsonExpression);

  public abstract void visit(TupleDeclarationExpression tupleDeclarationExpression);

  public abstract void visit(VerbaCodePage verbaCodePage);

  public abstract void visit(ReturnStatementExpression returnStatementExpression);

  public abstract void visit(SignatureDeclarationExpression signatureDeclarationExpression);

  public abstract void visit(QuoteExpression quoteExpression);

  public abstract void visit(AssignmentStatementExpression assignmentStatementExpression);

  public abstract void visit(NumericExpression expression);

  public abstract void visit(ValDeclarationStatement valDeclarationStatement);

  public abstract void visit(WithNsExpression withNsExpression);

  public abstract void visit(MarkupDeclarationExpression markupDeclarationExpression);

  public abstract void visit(DeclarationModifierExrpression declarationModifierExrpression);

  public abstract void visit(NewExpression newExpression);

  public abstract void visit(BooleanExpression expression);
}
