package com.verba.language.graph.validation.validation.parameters;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.validation.ExpressionValidator;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.expressions.containers.tuple.TupleDeclarationExpression;

import java.util.function.Predicate;

/**
 * Created by sircodesalot on 14-5-5.
 */
public class TupleDeclarationValidator extends ExpressionValidator<TupleDeclarationExpression> {
  public TupleDeclarationValidator(TupleDeclarationExpression tuple) {
    super(tuple);
  }

  public TupleDeclarationExpression tuple() {
    return super.target();
  }

  public QIterable<VerbaExpression> findViolatingArguments(Predicate<VerbaExpression> predicate) {
    return this.tuple().items().where(argument -> !predicate.test(argument));
  }
}
