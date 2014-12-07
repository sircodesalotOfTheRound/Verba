package com.verba.language.build;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventLauncher;
import com.verba.language.emit.images.ObjectImageSet;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.verbatim.persist.VerbatimFileGenerator;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.codestream.StringBasedCodeStream;
import com.verba.language.parse.expressions.StaticSpaceExpression;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.codepage.VerbaCodePage;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;

/**
 * Created by sircodesalot on 14/11/20.
 */
public class Build {
  private BuildProfile buildProfile;
  private StaticSpaceExpression staticSpace;
  private SymbolTable symbolTable;
  private ObjectImageSet images;
  private QIterable<BuildEvent> eventSubscribers;

  // TODO: Move build into a method, rather than part of the constructor.
  private Build(boolean isDebugBuild, VerbaCodePage page) {
    this.buildProfile = new BuildProfile(isDebugBuild);
    this.staticSpace = new StaticSpaceExpression(page);
    this.eventSubscribers = this.determineEventSubscribers();
    this.afterParse();
    this.symbolTable = this.associateSymbols(staticSpace);
    this.validateBuild();
    this.beforeCodeGeneration();
    this.images = this.generateObjectImages();
  }

  private QIterable<BuildEvent> determineEventSubscribers() {
    QIterable<BuildEvent> expressionsSubscribingToEvents =
      this.staticSpace.allExpressions()
        .ofType(BuildEvent.class);

    QIterable<BuildEvent> expressionsWithDelegatingSubscriptionObject =
      this.staticSpace.allExpressions()
        .ofType(BuildEvent.ContainsEventSubscriptionObject.class)
        .map(BuildEvent.ContainsEventSubscriptionObject::buildEventObject);

    QSet<BuildEvent> allSubscribingObjects = new QSet<>();
    allSubscribingObjects.add(expressionsSubscribingToEvents);
    allSubscribingObjects.add(expressionsWithDelegatingSubscriptionObject);

    return allSubscribingObjects;
  }

  private void afterParse() {
    BuildEventLauncher<BuildEvent.NotifyParsingBuildEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifyParsingBuildEvent.class, eventSubscribers);

    launcher.launchEvent(expression -> expression.afterParse(buildProfile, this.staticSpace));
  }

  private SymbolTable associateSymbols(StaticSpaceExpression staticSpace) {
    BuildEventLauncher<BuildEvent.NotifySymbolTableBuildEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifySymbolTableBuildEvent.class, eventSubscribers);

    launcher.launchEvent(expression -> expression.beforeSymbolsGenerated(buildProfile, this.staticSpace));
    SymbolTable symbolTable = new SymbolTable(staticSpace);
    launcher.launchEvent(expression -> expression.afterSymbolsGenerated(buildProfile, this.staticSpace, symbolTable));
    launcher.launchEvent(expression -> expression.onResolveSymbols(buildProfile, this.staticSpace, symbolTable));

    return symbolTable;
  }

  private void validateBuild() {
    BuildEventLauncher<BuildEvent.NotifyReadyToCompileEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifyReadyToCompileEvent.class, eventSubscribers);

    launcher.launchEvent(expression -> expression.validateReadyToCompile(buildProfile, staticSpace, symbolTable));
  }

  private void beforeCodeGeneration() {
    BuildEventLauncher<BuildEvent.NotifyCodeGenerationEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifyCodeGenerationEvent.class, eventSubscribers);

    launcher.launchEvent(expression -> expression.beforeCodeGeneration(buildProfile, staticSpace, symbolTable));
  }

  private ObjectImageSet generateObjectImages() {
    BuildEventLauncher<BuildEvent.NotifyObjectEmitEvent> launcher
      = new BuildEventLauncher<>(BuildEvent.NotifyObjectEmitEvent.class, eventSubscribers);

    QIterable<ObjectImage> images = launcher.launchEventWithResultValue(expression ->
      expression.onGenerateObjectImage(buildProfile, staticSpace, symbolTable));

    return new ObjectImageSet(images);
  }

  public SymbolTable symbolTable() { return this.symbolTable; }
  public QIterable<VerbaExpression> allExpressions() { return this.staticSpace.allExpressions(); }
  public Partition<Class, VerbaExpression> expressionsByType() { return this.staticSpace.expressionsByType(); }

  public ObjectImageSet images() { return this.images; }

  public boolean saveAssembly(String path) {
    VerbatimFileGenerator generator = new VerbatimFileGenerator(this.images);
    return generator.save(path);
  }

  public static Build fromString(boolean isDebugBuild, String code) {
    StringBasedCodeStream codeStream = new StringBasedCodeStream(code);
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("MemoryCodefile.v", codeStream);

    return new Build(true, VerbaCodePage.read(null, lexer));
  }
}
