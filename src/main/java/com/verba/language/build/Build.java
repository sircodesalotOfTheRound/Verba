package com.verba.language.build;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.configuration.BuildSpecification;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventSet;
import com.verba.language.emit.images.ObjectImageSet;
import com.verba.language.emit.verbatim.persist.VerbatimFileGenerator;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;

/**
 * Created by sircodesalot on 14/11/20.
 */
@Deprecated
public class Build {
  private BuildEventSet buildEventSet;
  private com.verba.language.build.BuildProfile buildProfile;
  private LitFileRootExpression staticSpace;
  private SymbolTable symbolTable;
  private ObjectImageSet images;
  private QIterable<BuildEvent> eventSubscribers;
  private final BuildSpecification configuration;
  private final VerbaCodePage page;

  public Build(BuildSpecification configuration) {
    this.configuration = configuration;
    this.page = this.configuration.codeUnits()
      .map(unit -> VerbaCodePage.fromFile(null, unit.path()))
      .single();
  }

  public void build() {
    this.buildProfile = new com.verba.language.build.BuildProfile(configuration);
    this.staticSpace = new LitFileRootExpression(page);
    this.buildEventSet = new BuildEventSet(buildProfile, staticSpace);
    this.buildEventSet.afterParse();

    if (!this.buildProfile.shouldCreateSymbolTable()) return;

    this.buildEventSet.beforeAssociateSymbols();
    this.symbolTable = new SymbolTable(this.staticSpace);
    this.buildEventSet.afterAssociateSymbols(this.symbolTable);

    this.buildEventSet.validateBuild(this.symbolTable);

    if (!this.buildProfile.shouldEmitCode()) return;

    this.buildEventSet.beforeCodeGeneration(this.symbolTable);

    this.images = this.buildEventSet.generateObjectImages(symbolTable);
  }

  public SymbolTable symbolTable() { return this.symbolTable; }
  public QIterable<VerbaExpression> allExpressions() { return this.staticSpace.allExpressions(); }
  public Partition<Class, VerbaExpression> expressionsByType() { return this.staticSpace.expressionsByType(); }

  public ObjectImageSet images() { return this.images; }

  public boolean saveAssembly(String path) {
    VerbatimFileGenerator generator = new VerbatimFileGenerator(this.images);
    return generator.save(path);
  }
}
