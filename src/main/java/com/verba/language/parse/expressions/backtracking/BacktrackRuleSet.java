package com.verba.language.parse.expressions.backtracking;

import com.javalinq.interfaces.QIterable;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.info.LexList;
import com.verba.language.parse.lexing.Lexer;

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
        VerbaExpression expression = rule.attempt(parent, lexer, restOfLine);
        if (expression != null) {
          expression.parse(parent, lexer);
          return (T)expression;
        }
      }
    }

    return null;
  }

  @Override
  public Iterator<BacktrackRule> iterator() {
    return this.rules.iterator();
  }
}
