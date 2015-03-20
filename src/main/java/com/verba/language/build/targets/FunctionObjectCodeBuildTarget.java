package com.verba.language.build.targets;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.FunctionObjectImageListArtifact;
import com.verba.language.build.targets.artifacts.StringTableArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.language.emit.images.types.specialized.FunctionObjectImage;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class FunctionObjectCodeBuildTarget extends BuildTarget {
  public FunctionObjectCodeBuildTarget() {
    super(SymbolTableArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (allDependenciesAvailableOrUpdated(build, artifact)) {
      SymbolTableArtifact symbolTableArtifact = build.getArtifactOfType(SymbolTableArtifact.class);
      FunctionObjectImageListArtifact functionImages = projectFunctionsToObjectImage(build);

      build.addArtifact(functionImages);
    }
  }

  private FunctionObjectImageListArtifact projectFunctionsToObjectImage(Build build) {
    SymbolTableArtifact symbolTableArtifact = build.getArtifactOfType(SymbolTableArtifact.class);
    StringTableArtifact stringTable = build.getArtifactOfType(StringTableArtifact.class);
    SymbolTable symbolTable = symbolTableArtifact.symbolTable();
    QIterable<FunctionDeclarationExpression> functions
      = symbolTableArtifact.getSymbolsOfType(FunctionDeclarationExpression.class);

    QList<FunctionObjectImage> functionObjectImages =
      functions.map(function -> new FunctionObjectImage(function, build, symbolTable, stringTable)).toList();

    return new FunctionObjectImageListArtifact(functionObjectImages);
  }
}
