package com.verba.language.graph.visitors;

import com.verba.language.parse.expressions.StaticSpaceExpression;
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
import com.verba.language.parse.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parse.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parse.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parse.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parse.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parse.expressions.withns.WithNsExpression;

/**
 * Created by sircodesalot on 14/9/12.
 */
public interface SyntaxGraphVisitor {
  void visit(BlockDeclarationExpression verbaExpressions);

  void visit(StaticSpaceExpression staticSpaceExpression);

  void visit(NamedValueExpression namedObjectDeclarationExpression);

  void visit(PolymorphicDeclarationExpression classDeclarationExpression);

  void visit(FunctionDeclarationExpression functionDeclarationExpression);

  void visit(ArrayDeclarationExpression arrayDeclarationExpression);

  void visit(JsonExpression jsonExpression);

  void visit(TupleDeclarationExpression tupleDeclarationExpression);

  void visit(VerbaCodePage verbaCodePage);

  void visit(ReturnStatementExpression returnStatementExpression);

  void visit(SignatureDeclarationExpression signatureDeclarationExpression);

  void visit(QuoteExpression quoteExpression);

  void visit(AssignmentStatementExpression assignmentStatementExpression);

  void visit(NumericExpression expression);

  void visit(ValDeclarationStatement valDeclarationStatement);

  void visit(WithNsExpression withNsExpression);

  void visit(MarkupDeclarationExpression markupDeclarationExpression);
}
