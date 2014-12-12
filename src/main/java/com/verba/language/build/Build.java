package com.verba.language.build;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventLauncher;
import com.verba.language.build.event.BuildEventSet;
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
  private final BuildEventSet buildEventSet;
  private BuildProfile buildProfile;
  private StaticSpaceExpression staticSpace;
  private SymbolTable symbolTable;
  private ObjectImageSet images;
  private QIterable<BuildEvent> eventSubscribers;

  // TODO: Move build into a method, rather than part of the constructor.
  private Build(boolean isDebugBuild, VerbaCodePage page) {
    this.buildProfile = new BuildProfile(isDebugBuild);
    this.staticSpace = new StaticSpaceExpression(page);
    this.buildEventSet = new BuildEventSet(buildProfile, staticSpace);
    this.buildEventSet.afterParse();

    this.buildEventSet.beforeAssociateSymbols();
    this.symbolTable = new SymbolTable(this.staticSpace);
    this.buildEventSet.afterAssociateSymbols(this.symbolTable);

    this.buildEventSet.validateBuild(this.symbolTable);

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

  public static Build fromString(boolean isDebugBuild, String code) {
    StringBasedCodeStream codeStream = new StringBasedCodeStream(code);
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("MemoryCodefile.v", codeStream);

    return new Build(true, VerbaCodePage.read(null, lexer));
  }
}
