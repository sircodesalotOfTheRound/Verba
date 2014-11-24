package com.verba.language.emit.buildtools;

import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.emit.codepage.VerbaCodePage;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.codestream.StringBasedCodeStream;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.lexing.VerbaMemoizingLexer;

import java.util.function.Consumer;

/**
 * Created by sircodesalot on 14/11/20.
 */
public class Build {
  private BuildAnalysis buildAnalysis;
  private StaticSpaceExpression staticSpace;
  private GlobalSymbolTable symbolTable;

  private Build(VerbaCodePage page) {
    this.staticSpace = this.afterParse(page);
    this.symbolTable = this.beforeSymbolTableAssociation(staticSpace);
    this.buildAnalysis = new BuildAnalysis(staticSpace, symbolTable);
    this.afterSymbolTableAssociation();
    this.beforeCodeGeneration();
  }

  private StaticSpaceExpression afterParse(VerbaCodePage page) {
    StaticSpaceExpression staticSpace = new StaticSpaceExpression(page);
    forEachExpression(staticSpace.allExpressions(), expression -> expression.buildProfile().afterParse(buildAnalysis));
    return staticSpace;
  }

  private GlobalSymbolTable beforeSymbolTableAssociation(StaticSpaceExpression staticSpace) {
    forEachExpression(staticSpace.allExpressions(),  expression -> {
      expression.buildProfile().beforeSymbolTableAssociation(buildAnalysis);
    });

    return new GlobalSymbolTable(staticSpace);
  }

  private void afterSymbolTableAssociation() {
    forEachExpression(staticSpace.allExpressions(),  expression ->  {
      expression.buildProfile().afterSymbolTableAssociation(buildAnalysis);
    });
  }

  private void beforeCodeGeneration() {
    forEachExpression(staticSpace.allExpressions(), expression -> {
      expression.buildProfile().beforeCodeGeneration(buildAnalysis);
    });
  }

  private void forEachExpression(Iterable<VerbaExpression> expressions, Consumer<VerbaExpression> callback) {
    for (VerbaExpression expression : expressions) {
      callback.accept(expression);
    }
  }

  public GlobalSymbolTable symbolTable() { return this.symbolTable; }
  public QIterable<VerbaExpression> allExpressions() { return this.staticSpace.allExpressions(); }
  public Partition<Class, VerbaExpression> expressionsByType() { return this.staticSpace.expressionsByType(); }

  public static Build fromString(String code) {
    StringBasedCodeStream codeStream = new StringBasedCodeStream(code);
    VerbaMemoizingLexer lexer = new VerbaMemoizingLexer("StringBasedCodes.v", codeStream);

    return new Build(VerbaCodePage.read(null, lexer));
  }
}
