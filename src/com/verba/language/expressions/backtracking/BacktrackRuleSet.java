package com.verba.language.expressions.backtracking;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 14-2-20.
 */
public class BacktrackRuleSet<T> implements Iterable<BacktrackRule> {
    private List<BacktrackRule> rules = new ArrayList<>();

    public BacktrackRuleSet<T> addRule(BacktrackRule rule) {
        this.rules.add(rule);
        return this;
    }

    public T resolve(VerbaExpression parent, Lexer lexer) {
        LexList restOfLine = lexer.peekToEndOfLine();

        for (BacktrackRule rule : this.rules) {
            if (rule.attemptIf(parent, lexer, restOfLine)) {
                T result = safeAttempt(rule, parent, lexer, restOfLine);
                if (result != null) return result;
            }
        }

        return null;
    }

    private T safeAttempt(BacktrackRule rule, VerbaExpression parent, Lexer lexer, LexList restOfLine) {
        try {
            return (T) rule.attempt(parent, lexer, restOfLine);
        } catch (MismatchException ex) { /* Do nothing */}

        return null;
    }

    @Override
    public Iterator<BacktrackRule> iterator() {
        return this.rules.iterator();
    }
}
