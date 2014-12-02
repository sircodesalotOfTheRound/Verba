package com.verba.language.emit.buildtools;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.rendering.images.ObjectImage;
import com.verba.language.graph.analysis.expressions.tools.BuildAnalysis;
import com.verba.language.graph.imagegen.function.FunctionGraph;
import com.verba.language.graph.symbols.table.tables.GlobalSymbolTable;
import com.verba.language.parsing.expressions.StaticSpaceExpression;
import com.verba.language.parsing.expressions.blockheader.functions.FunctionDeclarationExpression;

import java.util.function.Function;

/**
 * Created by sircodesalot on 14/11/25.
 */
public class ObjectImageGenerator {
  private final BuildAnalysis buildAnalysis;
  private final StaticSpaceExpression staticSpace;
  private final GlobalSymbolTable symbolTable;
  private final QIterable<ObjectImage> images;

  public ObjectImageGenerator(BuildAnalysis buildAnalysis,
                              StaticSpaceExpression staticSpace,
                              GlobalSymbolTable symbolTable) {

    this.buildAnalysis = buildAnalysis;
    this.staticSpace = staticSpace;
    this.symbolTable = symbolTable;

    this.images = this.generateImages();
  }

  public QIterable<ObjectImage> images() { return this.images; }

  private QIterable<ObjectImage> generateImages() {
    QList<ObjectImage> images = new QList<>();
    images.add(this.generateImage(FunctionDeclarationExpression.class,
      function -> new FunctionGraph(function, staticSpace).createImage()));

    return images;
  }

  private <T> QIterable<ObjectImage> generateImage(Class<T> type, Function<T, ObjectImage> projection) {
    QList<ObjectImage> images = new QList<>();
    if (this.staticSpace.containsExpressionsOfType(type)) {
      QIterable<T> expressions = this.staticSpace.expressionsByType().get(type).cast(type);

      for (T expression : expressions) {
        images.add(projection.apply(expression));
      }
    }

    return images;
  }
}
