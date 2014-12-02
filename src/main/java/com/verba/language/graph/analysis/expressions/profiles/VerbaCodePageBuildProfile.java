package com.verba.language.graph.analysis.expressions.profiles;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.codepage.VerbaCodePage;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.analysis.expressions.tools.BuildProfile;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.withns.WithNsExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class VerbaCodePageBuildProfile extends BuildProfile<VerbaCodePage> {
  private QList<String> namespaces = new QList<>();

  public VerbaCodePageBuildProfile(VerbaCodePage expression) {
    super(expression);
  }

  @Override
  public void afterParse(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) {
    QIterable<String> namespaceRepresentations = expression
      .expressionsByType(WithNsExpression.class)
      .map(ns -> ns.namespace().representation());

    this.namespaces.add(namespaceRepresentations);
  }

  @Override
  public void beforeSymbolTableAssociation(BuildAnalysis analysis, StaticSpaceExpression buildAnalysis) {

  }

  @Override
  public void afterSymbolTableAssociation(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {

  }

  @Override
  public void beforeCodeGeneration(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {

  }

  public QIterable<String> namespaces() { return namespaces; }
}
