package com.verba.language.graph.visitors;

import com.verba.language.parsing.expressions.blockheader.classes.PolymorphicExpression;
import com.verba.language.parsing.expressions.codepage.VerbaCodePage;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.functions.SignatureDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.varname.NamedValueExpression;
import com.verba.language.parsing.expressions.containers.array.ArrayDeclarationExpression;
import com.verba.language.parsing.expressions.containers.json.JsonExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.rvalue.simple.NumericExpression;
import com.verba.language.parsing.expressions.rvalue.simple.QuoteExpression;
import com.verba.language.parsing.expressions.statements.assignment.AssignmentStatementExpression;
import com.verba.language.parsing.expressions.statements.declaration.ValDeclarationStatement;
import com.verba.language.parsing.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.parsing.expressions.withns.WithNsExpression;

/**
 * Created by sircodesalot on 14/9/12.
 */
public interface SyntaxGraphVisitor {
  void visit(BlockDeclarationExpression verbaExpressions);

  void visit(StaticSpaceExpression staticSpaceExpression);

  void visit(NamedValueExpression namedObjectDeclarationExpression);

  void visit(PolymorphicExpression classDeclarationExpression);

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
}
