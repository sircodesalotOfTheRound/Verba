package com.verba.language.build.targets;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.LitFileSyntaxTreeArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.VerbaExpression;

/**
 * Notify the syntax tree that certain build resources are available.
 */
public class SyntaxTreeNotificationBuildTarget extends BuildTarget {
  public SyntaxTreeNotificationBuildTarget() {
    super(LitFileSyntaxTreeArtifact.class, SymbolTableArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact symbolTable) {
    if (symbolTable instanceof SymbolTableArtifact) {
      this.notifyExpressionsParseComplete(build, build.getArtifactOfType(LitFileSyntaxTreeArtifact.class));
      this.notifyExpressionsSymbolTableComplete(build, (SymbolTableArtifact) symbolTable);
    }
  }


  private void notifyExpressionsParseComplete(Build build, LitFileSyntaxTreeArtifact syntaxTree) {
    for (VerbaExpression expression : syntaxTree.rootExpression().allExpressions()) {
      expression.afterContentsParsed(build);
    }
  }

  private void notifyExpressionsSymbolTableComplete(Build build, SymbolTableArtifact symbolTableArtifact) {
    LitFileSyntaxTreeArtifact syntaxTree = build.getArtifactOfType(LitFileSyntaxTreeArtifact.class);
    SymbolTable symbolTable = symbolTableArtifact.symbolTable();
    QIterable<VerbaExpression> allExpressions = syntaxTree.rootExpression().allExpressions();

    // Notify all expressions that the symbol table is available.
    for (VerbaExpression expression : allExpressions) {
      expression.afterSymbolsGenerated(build, symbolTable);
    }

    // Notify all expressions that symbols can now be resolved.
    for (VerbaExpression expression : allExpressions) {
      expression.onResolveSymbols(build, symbolTable);
    }
  }


}
