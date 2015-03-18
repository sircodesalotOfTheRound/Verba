package com.verba.language.build.targets;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.SourceCodeSyntaxTreeListArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableBySourceCodePageArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolsBySourceFileBuildTarget extends BuildTarget {
  public static class SourceCodePageSymbolTable {
    private final VerbaCodePage page;
    private final SymbolTableArtifact symbolTable;

    public SourceCodePageSymbolTable(VerbaCodePage page) {
      this.page = page;
      this.symbolTable = new SymbolTableArtifact(page);
    }

    public String path() { return this.page.path(); }
    public VerbaCodePage page() { return this.page; }
    public SymbolTableArtifact symbolTable() { return this.symbolTable; }
  }

  public SymbolsBySourceFileBuildTarget() {
    super(SourceCodeSyntaxTreeListArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (allDependenciesAvailableOrUpdated(build, artifact)) {
      SourceCodeSyntaxTreeListArtifact sources = build.getArtifactOfType(SourceCodeSyntaxTreeListArtifact.class);
      QIterable<SourceCodePageSymbolTable> symbolTables = sources.syntaxTrees()
        .map(SourceCodePageSymbolTable::new);

      SymbolTableBySourceCodePageArtifact symbolTableArtifact = new SymbolTableBySourceCodePageArtifact(symbolTables);
      build.addArtifact(symbolTableArtifact);
    }
  }
}
