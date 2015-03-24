package com.verba.language.graph.visitors;

import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;

/**
 * Created by sircodesalot on 15/3/23.
 */
public interface FunctionGraphNode {
  VirtualVariable accept(FunctionGraphVisitor visitor);
}
