package com.verba.language.parsing.expressions.blockheader.functions;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.symbols.table.tables.ScopedSymbolTable;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.block.BlockDeclarationExpression;
import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parsing.expressions.categories.*;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.parsing.expressions.members.MemberExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.identifiers.KeywordToken;
import com.verba.language.parsing.tokens.operators.mathop.OperatorToken;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14-2-17.
 */
public class SignatureDeclarationExpression extends VerbaExpression implements NamedExpression,
  TypedExpression, InvokableExpression, GenericallyParameterizedExpression, SymbolTableExpression {

  private final FullyQualifiedNameExpression identifier;
  private TypeDeclarationExpression returnType;

  public SignatureDeclarationExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(KeywordToken.class, "signature");
    this.identifier = FullyQualifiedNameExpression.read(this, lexer);

    if (lexer.currentIs(OperatorToken.class, ":")) {
      lexer.readCurrentAndAdvance(OperatorToken.class, ":");
      this.returnType = TypeDeclarationExpression.read(this, lexer);
    }

    this.closeLexingRegion();
  }

  public static SignatureDeclarationExpression read(VerbaExpression parent, Lexer lexer) {
    return new SignatureDeclarationExpression(parent, lexer);
  }


  public boolean hasGenericParameters() {
    return this.primaryIdentifier().hasGenericParameters();
  }

  public boolean hasParameters() {
    return (this.primaryIdentifier().hasParameters());
  }

  public MemberExpression primaryIdentifier() {
    return this.declaration().first();
  }

  public FullyQualifiedNameExpression declaration() {
    return this.identifier;
  }

  @Override
  public QIterable<TupleDeclarationExpression> parameterSets() {
    return this.primaryIdentifier().parameterLists();
  }

  @Override
  public BlockDeclarationExpression block() {
    throw new NotImplementedException();
  }

  public GenericTypeListExpression genericParameters() {
    return this.primaryIdentifier().genericParameterList();
  }

  @Override
  public String name() {
    return this.primaryIdentifier().memberName();
  }

  @Override
  public boolean hasTypeConstraint() {
    return this.returnType != null;
  }

  @Override
  public TypeDeclarationExpression typeDeclaration() {
    return this.returnType;
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(ScopedSymbolTable symbolTable) {
    symbolTable.visit(this);
  }
}
