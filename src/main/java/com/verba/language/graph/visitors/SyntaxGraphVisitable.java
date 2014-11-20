package com.verba.language.graph.visitors;

/**
 * Created by sircodesalot on 14/9/12.
 */
public interface SyntaxGraphVisitable {
  void accept(SyntaxGraphVisitor visitor);
}
