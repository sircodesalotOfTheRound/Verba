package com.verba.language.graph.analysis.expressions.tools;

import com.javalinq.interfaces.QIterable;
import com.verba.language.graph.validation.violations.ValidationError;
import com.verba.language.graph.validation.violations.ValidationViolation;
import com.verba.language.graph.validation.violations.ValidationViolationList;
import com.verba.language.graph.validation.violations.ValidationWarning;
import com.verba.language.parsing.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14/11/22.
 */
public abstract class BuildProfile<T> implements BuildProfileBase {
  private ValidationViolationList violations;
  protected final T expression;
  public BuildProfile(T expression) {
    this.expression = expression;
  }

  public void addErrorViolation(VerbaExpression expression, String format, Object... args) {
    this.violations.addError(expression, format, args);
  }

  public void addWarningViolation(VerbaExpression expression, String format, Object... args) {
    this.violations.addWarning(expression, format, args);
  }

  public QIterable<ValidationViolation> violations() { return this.violations; }
  public QIterable<ValidationError> errors() { return this.violations.ofType(ValidationError.class); }
  public QIterable<ValidationWarning> warnings() { return this.violations.ofType(ValidationWarning.class); }
  @Override
  public void afterParse(BuildAnalysis buildAnalysis) { }

  @Override
  public void beforeSymbolTableAssociation(BuildAnalysis buildAnalysis) { }

  @Override
  public void afterSymbolTableAssociation(BuildAnalysis buildAnalysis) { }

  @Override
  public void beforeCodeGeneration(BuildAnalysis buildAnalysis) { }
}
