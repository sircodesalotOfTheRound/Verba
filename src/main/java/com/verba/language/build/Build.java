package com.verba.language.build;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventLauncher;
import com.verba.language.emit.images.ObjectImageSet;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.types.basic.InMemoryObjectImage;
import com.verba.language.parsing.expressions.codepage.VerbaCodePage;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.codestream.StringBasedCodeStream;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.lexing.VerbaMemoizingLexer;

/**
 * Created by sircodesalot on 14/11/20.
 */
public class Build {
  private BuildAnalysis buildAnalysis;
  private StaticSpaceExpression staticSpace;
  private GlobalSymbolTable symbolTable;
  private ObjectImageSet images;

  // TODO: Move build into a method, rather than part of the constructor.
  private Build(boolean isDebugBuild, VerbaCodePage page) {
    this.buildAnalysis = new BuildAnalysis(isDebugBuild);
    this.staticSpace = this.afterParse(page);
    this.symbolTable = this.beforeSymbolTableAssociation(staticSpace);
    this.beforeCodeGeneration();
    this.images = this.generateObjectImages();
  }

  private StaticSpaceExpression afterParse(VerbaCodePage page) {
    StaticSpaceExpression staticSpace = new StaticSpaceExpression(page);
    BuildEventLauncher<BuildEvent.NotifyParsingBuildEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifyParsingBuildEvent.class, staticSpace.allExpressions());

    launcher.launchEvent(expression -> expression.afterParse(buildAnalysis, this.staticSpace));
    return staticSpace;
  }

  private GlobalSymbolTable beforeSymbolTableAssociation(StaticSpaceExpression staticSpace) {
    BuildEventLauncher<BuildEvent.NotifySymbolTableBuildEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifySymbolTableBuildEvent.class, this.allExpressions());

    launcher.launchEvent(expression -> expression.beforeSymbolTableAssociation(buildAnalysis, this.staticSpace));
    GlobalSymbolTable symbolTable = new GlobalSymbolTable(staticSpace);
    launcher.launchEvent(expression -> expression.afterSymbolTableAssociation(buildAnalysis, this.staticSpace, symbolTable));

    return symbolTable;
  }

  private void beforeCodeGeneration() {
    BuildEventLauncher<BuildEvent.NotifyCodeGenerationEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifyCodeGenerationEvent.class, this.allExpressions());

    launcher.launchEvent(expression -> expression.beforeCodeGeneration(buildAnalysis, staticSpace, symbolTable));
  }

  private ObjectImageSet generateObjectImages() {
    BuildEventLauncher<BuildEvent.NotifyObjectEmitEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifyObjectEmitEvent.class, this.allExpressions());

    QIterable<ObjectImage> images = launcher.launchEventWithResultValue(expression ->
      expression.onGenerateObjectImage(buildAnalysis, staticSpace, symbolTable));

    return new ObjectImageSet(images);
  }

  public GlobalSymbolTable symbolTable() { return this.symbolTable; }
  public QIterable<VerbaExpression> allExpressions() { return this.staticSpace.allExpressions(); }
  public Partition<Class, VerbaExpression> expressionsByType() { return this.staticSpace.expressionsByType(); }

  public ObjectImageSet images() { return this.images; }

  public static Build fromString(boolean isDebugBuild, String code) {
    StringBasedCodeStream codeStream = new StringBasedCodeStream(code);
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("MemoryCodefile.v", codeStream);

    return new Build(true, VerbaCodePage.read(null, lexer));
  }
}
