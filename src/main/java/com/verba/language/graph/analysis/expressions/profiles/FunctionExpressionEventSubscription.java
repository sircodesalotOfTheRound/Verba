package com.verba.language.graph.analysis.expressions.profiles;

import com.verba.language.build.event.BuildEvent;
import com.verba.language.emit.images.interfaces.ObjectImage;
import com.verba.language.emit.images.types.specialized.FunctionObjectImage;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.analysis.expressions.tools.ExpressionBuildEventSubscription;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;

/**
 * Created by sircodesalot on 14/11/23.
 */
public class FunctionExpressionEventSubscription extends ExpressionBuildEventSubscription<FunctionDeclarationExpression>
  implements BuildEvent.NotifyObjectEmitEvent
{
  public FunctionExpressionEventSubscription(FunctionDeclarationExpression expression) {
    super(expression);
  }

  @Override
  public ObjectImage onGenerateObjectImage(BuildAnalysis buildAnalysis, StaticSpaceExpression staticSpace, GlobalSymbolTable symbolTable) {
    FunctionObjectImage image = new FunctionObjectImage(this.expression(), staticSpace);

    if (buildAnalysis.isDebugBuild()) {
      image.displayCoreDump();
    }

    return image;
  }
}
