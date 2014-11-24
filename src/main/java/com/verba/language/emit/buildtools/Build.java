package com.verba.language.emit.buildtools;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.lexing.VerbaMemoizingLexer;
import com.verba.language.parsing.codestream.StringBasedCodeStream;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;

/**
 * Created by sircodesalot on 14/11/20.
 */
public class Build {
  private BuildAnalysis buildAnalysis = new BuildAnalysis();
  private StaticSpaceExpression staticSpace;
  private GlobalSymbolTable symbolTable;

  private Build(VerbaCodePage page) {
    this.afterParse(page);
    this.staticSpace = new StaticSpaceExpression(page);
    this.symbolTable = staticSpace.symbolTable();
  }

  private void afterParse(VerbaExpression rootExpression) {
    rootExpression.expressionAnalysis().afterParse(buildAnalysis);
  }

  private void beforeSymbolTableAssociation() { }
  private void afterSymbolTableAssociation() { }
  private void beforeCodeGeneration() { }

  public GlobalSymbolTable symbolTable() { return this.symbolTable; }
  public QIterable<VerbaExpression> allExpressions() { return this.staticSpace.allExpressions(); }
  public Partition<Class, VerbaExpression> expressionsByType() { return this.staticSpace.expressionsByType(); }

  public static Build fromString(String code) {
    StringBasedCodeStream codeStream = new StringBasedCodeStream(code);
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("StringBasedCodes.v", codeStream);

    return new Build(VerbaCodePage.read(null, lexer));
  }
}
