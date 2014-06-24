package com.verba.language.expressions;

import com.verba.language.expressions.backtracking.BacktrackRuleSet;
import com.verba.language.expressions.backtracking.rules.*;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.info.LexInfo;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class VerbaExpression {
    private static BacktrackRuleSet<VerbaExpression> rules
        = new BacktrackRuleSet<VerbaExpression>()
        .addRule(new RpnBacktrackRule())
        .addRule(new LiteralExpressionRule())
        .addRule(new FunctionDeclarationBacktrackRule())
        .addRule(new NamespaceDeclarationBacktrackRule())
        .addRule(new ClassDeclarationBacktrackRule())
        .addRule(new ValDeclarationBacktrackRule())
        .addRule(new MutaDeclarationBacktrackRule())
        .addRule(new IfStatementBacktrackRule())
        .addRule(new VarNameExpressionBacktrackRule())
        .addRule(new GrabExpressionBacktrackRule())
        .addRule(new ForStatementBacktrackRule())
        .addRule(new WhileStatementBacktrackRule())
        .addRule(new AssignmentStatementBacktrackRule())
        .addRule(new TaskDeclarationBacktrackRule())
        .addRule(new InjectedClassDeclarationBacktrackRule())
        .addRule(new MetaDeclarationBacktrackRule())
        .addRule(new TraitDeclarationBacktrackRule())
        .addRule(new ExtendDeclarationBacktrackRule())
        .addRule(new SignatureDeclarationBacktrackRule())
        .addRule(new HashtagDeclarationBacktrackRule())
        .addRule(new AspectDeclarationBacktrackRule())
        .addRule(new ReturnStatementRule());

    private VerbaExpression parent;
    private final LexInfo lexInfo;

    public VerbaExpression(VerbaExpression parent, Lexer lexer) {
        this.parent = parent;
        this.lexInfo = (lexer != null && lexer.notEOF()) ? lexer.current() : null;
    }

    // Parent
    public VerbaExpression parent() {
        return this.parent;
    }

    public void setParent(VerbaExpression parent) {
        this.parent = parent;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    // Lex Info
    public LexInfo lexInfo() {
        return this.lexInfo;
    }

    public int line() {
        return this.lexInfo.line();
    }

    public int column() {
        return this.lexInfo.column();
    }

    public int absolutePosition() {
        return this.lexInfo.absolutePosition();
    }

    // Testing
    public <T> boolean is(Class<T> type) {
        return type.isAssignableFrom(this.getClass());
    }

    public <T> boolean parentIs(Class<T> type) {
        return this.hasParent() && type.isAssignableFrom(this.getClass());
    }

    public static VerbaExpression read(VerbaExpression parent, Lexer lexer) {
        return rules.resolve(parent, lexer);
    }
}
