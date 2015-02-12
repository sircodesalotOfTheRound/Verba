package com.verba.language.build.event.subscriptions;

import com.verba.language.build.BuildProfile;
import com.verba.language.build.event.BuildEvent;
import com.verba.language.build.event.BuildEventSubscription;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.types.specialized.FunctionObjectImage;
import com.verba.language.graph.symbols.resolution.FunctionReturnTypeResolver;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.parse.expressions.LitFileRootExpression;
import com.verba.language.parse.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class FunctionEventSubscription extends BuildEventSubscription<FunctionDeclarationExpression>
  implements BuildEvent.NotifySymbolTableBuildEvent, BuildEvent.NotifyObjectEmitEvent
{
  private Symbol returnType;
  public FunctionEventSubscription(FunctionDeclarationExpression expression) {
    super(expression);
  }

  @Override
  public void beforeSymbolsGenerated(BuildProfile analysis, LitFileRootExpression buildAnalysis) {

  }

  @Override
  public void afterSymbolsGenerated(BuildProfile buildProfile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {

  }

  @Override
  public void onResolveSymbols(BuildProfile profile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    this.returnType = this.determineReturnType(symbolTable);
  }

  private Symbol determineReturnType(SymbolTable symbolTable) {
    FunctionReturnTypeResolver returnTypeResolver = new FunctionReturnTypeResolver(symbolTable, this.expression());
    return returnTypeResolver.resolve();
  }

  @Override
  public ObjectImage onGenerateObjectImage(BuildProfile buildProfile, LitFileRootExpression staticSpace, SymbolTable symbolTable) {
    FunctionObjectImage image = new FunctionObjectImage(this.expression(),
      buildProfile, staticSpace, symbolTable, buildProfile.stringTable());

    if (buildProfile.isDebugBuild()) {
      image.displayCoreDump();
    }

    return image;
  }

  public Symbol returnType() { return this.returnType; }
}
