package com.verba.language.graph.events;

import com.verba.language.build.configuration.Build;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.graph.events.interfaces.VerbaExpressionBuildEvent;
import com.verba.language.graph.events.interfaces.VerbaExpressionBuildEventSubscription;
import com.verba.language.graph.symbols.resolution.FunctionReturnTypeResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class FunctionEventSubscriptionVerbaExpression extends VerbaExpressionBuildEventSubscription<FunctionDeclarationExpression>
  implements VerbaExpressionBuildEvent.NotifySymbolTableVerbaExpressionBuildEvent, VerbaExpressionBuildEvent.NotifyObjectEmitEventVerbaExpression
{

  private Symbol returnType;
  public FunctionEventSubscriptionVerbaExpression(FunctionDeclarationExpression expression) {
    super(expression);
  }

  @Override
  public void beforeSymbolsGenerated(Build analysis, LitFileRootExpression buildAnalysis) {

  }

  @Override
  public void afterSymbolsGenerated(Build buildProfile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }

  @Override
  public void onResolveSymbols(Build profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.returnType = this.determineReturnType(symbolTable);
  }

  private Symbol determineReturnType(SymbolTable symbolTable) {
    FunctionReturnTypeResolver returnTypeResolver = new FunctionReturnTypeResolver(symbolTable, this.expression());
    return returnTypeResolver.resolve();
  }

  @Override
  public ObjectImage onGenerateObjectImage(Build buildProfile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    throw new NotImplementedException();
    /*FunctionObjectImage image = new FunctionObjectImage(this.expression(),
      buildProfile, staticSpace, symbolTable, buildProfile.stringTable());

    if (buildProfile.isDebugBuild()) {
      image.displayCoreDump();
    }

    return image;*/
  }

  public Symbol returnType() { return this.returnType; }
}
