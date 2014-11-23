package com.verba.language.parsing.expressions.backtracking;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parsing.expressions.VerbaExpression;
import com.verba.language.parsing.lexing.Lexer;
import com.verba.language.parsing.info.LexList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 14-2-20.
 */
public class BacktrackRuleSet<T> implements QIterable<BacktrackRule> {
  private List<BacktrackRule> rules = new ArrayList<>();

  public BacktrackRuleSet<T> addRule(BacktrackRule rule) {
    this.rules.add(rule);
    return this;
  }

  public BacktrackRuleSet () { }
  public BacktrackRuleSet (Iterable<BacktrackRule> rules) {
    for (BacktrackRule rule : rules) {
      this.addRule(rule);
    }
  }

  public T resolve(VerbaExpression parent, Lexer lexer) {
    LexList restOfLine = lexer.peekToEndOfLine();

    for (BacktrackRule rule : this.rules) {
      if (rule.attemptIf(parent, lexer, restOfLine)) {
        T result = (T)rule.attempt(parent, lexer, restOfLine);
        if (result != null) return result;
      }
    }

    return null;
  }

  @Override
  public Iterator<BacktrackRule> iterator() {
    return this.rules.iterator();
  }
}
