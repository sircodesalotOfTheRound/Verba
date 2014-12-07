package com.verba.language.build.event;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.expressions.VerbaExpression;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class BuildEventLauncher<T extends BuildEvent> {
  private final QIterable<T> expressions;

  public BuildEventLauncher(Class<T> eventType, QIterable<BuildEvent> expressions) {
    this.expressions = expressions.ofType(eventType);
  }

  public void launchEvent(Consumer<T> callback) {
    for (T expression : expressions) {
      callback.accept(expression);
    }
  }

  public <TResult> QIterable<TResult> launchEventWithResultValue(Function<T, TResult> projection) {
    return this.expressions.map(projection).toList();
  }
}
