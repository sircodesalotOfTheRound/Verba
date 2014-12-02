package com.verba.language.emit.buildtools;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.emit.rendering.images.ObjectImage;
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
  private BuildAnalysis buildAnalysis = new BuildAnalysis();
  private StaticSpaceExpression staticSpace;
  private GlobalSymbolTable symbolTable;
  private ObjectImageGenerator images;

  private Build(VerbaCodePage page) {
    this.staticSpace = this.afterParse(page);
    this.symbolTable = this.beforeSymbolTableAssociation(staticSpace);
    this.afterSymbolTableAssociation();
    this.beforeCodeGeneration();
    this.images = this.generateObjectImages();
  }


  private StaticSpaceExpression afterParse(VerbaCodePage page) {
    StaticSpaceExpression staticSpace = new StaticSpaceExpression(page);

    for (VerbaExpression expression : staticSpace.allExpressions()) {
      expression.buildProfile().afterParse(buildAnalysis, staticSpace);
    }

    return staticSpace;
  }

  private GlobalSymbolTable beforeSymbolTableAssociation(StaticSpaceExpression staticSpace) {
    for (VerbaExpression expression : this.staticSpace.allExpressions()) {
      expression.buildProfile().beforeSymbolTableAssociation(buildAnalysis, staticSpace);
    }

    return new GlobalSymbolTable(staticSpace);
  }

  private void afterSymbolTableAssociation() {
    for (VerbaExpression expression : this.staticSpace.allExpressions()) {
      expression.buildProfile().afterSymbolTableAssociation(buildAnalysis, staticSpace, symbolTable);
    }
  }

  private void beforeCodeGeneration() {
    for (VerbaExpression expression : this.staticSpace.allExpressions()) {
      expression.buildProfile().beforeCodeGeneration(buildAnalysis, staticSpace, symbolTable);
    }
  }

  private ObjectImageGenerator generateObjectImages() {
    return new ObjectImageGenerator(buildAnalysis, staticSpace, symbolTable);
  }

  public GlobalSymbolTable symbolTable() { return this.symbolTable; }
  public QIterable<VerbaExpression> allExpressions() { return this.staticSpace.allExpressions(); }
  public Partition<Class, VerbaExpression> expressionsByType() { return this.staticSpace.expressionsByType(); }

  public QIterable<ObjectImage> images() { return this.images.images(); }

  public static Build fromString(String code) {
    StringBasedCodeStream codeStream = new StringBasedCodeStream(code);
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("MemoryCodefile.v", codeStream);

    return new Build(VerbaCodePage.read(null, lexer));
  }
}
