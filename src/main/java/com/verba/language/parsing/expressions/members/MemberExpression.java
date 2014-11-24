package com.verba.language.parsing.expressions.members;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.analysis.expressions.tools.BuildProfileBase;
import com.verba.language.graph.visitors.SyntaxGraphVisitor;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.blockheader.generic.GenericTypeListExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.parsing.expressions.rvalue.simple.IdentifierExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.tokens.operators.enclosure.EnclosureToken;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class MemberExpression extends VerbaExpression {
  private final IdentifierExpression identifier;
  private final GenericTypeListExpression genericParameters;
  private final QList<TupleDeclarationExpression> parameterLists = new QList<>();

  public MemberExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.identifier = IdentifierExpression.read(this, lexer);
    this.genericParameters = GenericTypeListExpression.read(this, lexer);

    // Read parameterSets if they exist
    do {
      if (lexer.currentIs(EnclosureToken.class, "(")) {
        parameterLists.add(TupleDeclarationExpression.read(this, lexer));
      }
    } while (lexer.notEOF() && lexer.currentIs(EnclosureToken.class, "("));

    this.closeLexingRegion();
  }

  public static MemberExpression read(VerbaExpression parent, Lexer lexer) {
    return new MemberExpression(parent, lexer);
  }

  @Override
  public BuildProfileBase buildProfile() {
    return null;
  }

  public String memberName() {
    return this.identifier.representation();
  }

  public String representation() {
    if (genericParameters.hasItems()) {
      return String.format("%s%s",
        this.identifier.representation(),
        this.genericParameters.representation());
    } else {
      return this.identifier.representation();
    }
  }

  public boolean hasParameters() {
    return this.parameterLists.any();
  }

  public IdentifierExpression identifier() {
    return this.identifier;
  }

  public QIterable<TupleDeclarationExpression> parameterLists() {
    return this.parameterLists;
  }

  public GenericTypeListExpression genericParameterList() {
    return this.genericParameters;
  }

  public boolean hasGenericParameters() {
    return this.genericParameters.hasItems();
  }

  @Override
  public void accept(SyntaxGraphVisitor visitor) {

  }
}
