package com.verba.language.build.targets;

import com.javalinq.interfaces.QIterable;
import com.verba.language.build.configuration.Build;
import com.verba.language.build.targets.artifacts.SourceCodeSyntaxTreeListArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableArtifact;
import com.verba.language.build.targets.artifacts.SymbolTableBySourceCodePageArtifact;
import com.verba.language.build.targets.artifacts.interfaces.BuildArtifact;
import com.verba.language.build.targets.interfaces.BuildTarget;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class SymbolsBySourceFileBuildTarget extends BuildTarget {
  public static class SourceCodeInfo {
    private final VerbaSourceCodeFile page;
    private final String hash;
    private final SymbolTableArtifact symbolTable;

    public SourceCodeInfo(VerbaSourceCodeFile page) {
      this.page = page;
      this.hash = DigestUtils.sha1Hex(page.text());
      this.symbolTable = new SymbolTableArtifact(page);
    }

    public String path() { return this.page.path(); }
    public VerbaSourceCodeFile page() { return this.page; }
    public String hash() { return this.hash; }
    public SymbolTableArtifact symbolTable() { return this.symbolTable; }
  }

  public SymbolsBySourceFileBuildTarget() {
    super(SourceCodeSyntaxTreeListArtifact.class);
  }

  @Override
  public void onBuildUpdated(Build build, BuildArtifact artifact) {
    if (allDependenciesAvailableOrUpdated(build, artifact)) {
      SourceCodeSyntaxTreeListArtifact sources = build.getArtifactOfType(SourceCodeSyntaxTreeListArtifact.class);
      QIterable<SourceCodeInfo> symbolTables = sources.syntaxTrees()
        .map(SourceCodeInfo::new);

      SymbolTableBySourceCodePageArtifact symbolTableArtifact = new SymbolTableBySourceCodePageArtifact(symbolTables);
      build.addArtifact(symbolTableArtifact);
    }
  }
}
