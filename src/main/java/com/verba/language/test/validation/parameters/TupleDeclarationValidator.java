package com.verba.language.test.validation.parameters;

import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.containers.tuple.TupleDeclarationExpression;
import com.verba.language.test.validation.ExpressionValidator;

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
