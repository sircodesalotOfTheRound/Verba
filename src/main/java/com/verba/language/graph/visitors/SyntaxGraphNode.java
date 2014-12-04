package com.verba.language.graph.visitors;

/**
 * Created by sircodesalot on 14/9/12.
 */
public interface SyntaxGraphNode {
  void accept(SyntaxGraphVisitor visitor);
}
