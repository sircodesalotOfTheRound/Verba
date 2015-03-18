package com.verba.language.build.targets;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableBySourceCodePageArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.language.parse.expressions.blockheader.classes.PolymorphicDeclarationExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolFileListBuildTarget extends BuildTarget {
  public SymbolFileListBuildTarget() {
    super(SymbolTableBySourceCodePageArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if(allDependenciesAvailableOrUpdated(build, artifact)) {
      SymbolTableBySourceCodePageArtifact symbolTablesList =
        build.getArtifactOfType(SymbolTableBySourceCodePageArtifact.class);

      for (SymbolsBySourceFileBuildTarget.SourceCodeInfo info : symbolTablesList.symbolTables()) {
        this.emitSymbolFile(info);
      }
    }
  }

  private void emitSymbolFile(SymbolsBySourceFileBuildTarget.SourceCodeInfo info) {
    if (requiresUpdate(info)) {
      SymbolTableArtifact artifact = info.symbolTable();

      emitPolymorphicExpressions(artifact.getSymbolsOfType(PolymorphicDeclarationExpression.class));
      emitFunctions(artifact.getSymbolsOfType(FunctionDeclarationExpression.class));
    }
  }

  private boolean requiresUpdate(SymbolsBySourceFileBuildTarget.SourceCodeInfo info) {
    return false;
  }

  private void emitPolymorphicExpressions(QIterable<PolymorphicDeclarationExpression> expressions) {

  }

  private void emitFunctions(QIterable<FunctionDeclarationExpression> functions) {

  }


}
