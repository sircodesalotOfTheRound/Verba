package com.verba.language.tools;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.expressions.codepage.VerbaSourceCodeFile;
import com.verba.language.parse.expressions.withns.WithNsExpression;

import java.util.Iterator;

/**
 * Created by sircodesalot on 15/3/20.
 */
public class ImportedNamespaceSet implements QIterable<String> {
  private final QSet<String> namespaces;

  public ImportedNamespaceSet(VerbaSourceCodeFile source) {
    this.namespaces = determineNamespaces(source);
  }

  private QSet<String> determineNamespaces(VerbaSourceCodeFile source) {
    return source
      .childExpressions()
      .ofType(WithNsExpression.class)
      .map(ns -> ns.namespace().representation())
      .toSet();
  }

  public boolean containsNamespace(String namespace) { return this.namespaces.contains(namespace); }

  @Override
  public Iterator<String> iterator() {
    return this.namespaces.iterator();
  }
}
