package com.verba.language.expressions;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.ast.VerbaASTNodeCollector;
import com.verba.language.expressions.categories.SymbolTableExpression;
import com.verba.language.symbols.table.tables.GlobalSymbolTable;

/**
 * Created by sircodesalot on 14-5-14.
 */
public class StaticSpaceExpression extends VerbaExpression implements SymbolTableExpression {
    private GlobalSymbolTable symbolTable;
    private VerbaASTNodeCollector allSubExpressions;
    private final QList<VerbaExpression> rootExpressions = new QList<>();

    public StaticSpaceExpression(Iterable<VerbaExpression> rootExpressions) {
        super(null, null);

        for (VerbaExpression expression : rootExpressions)
            this.rootExpressions.add(expression);

        this.update();
    }

    public StaticSpaceExpression(VerbaExpression... rootExpressions) {
        super(null, null);

        for (VerbaExpression expression : rootExpressions)
            this.rootExpressions.add(expression);

        this.update();
    }

    public QIterable<VerbaExpression> rootLevelExpressions() {
        return this.rootExpressions;
    }

    public GlobalSymbolTable globalSymbolTable() {
        return this.symbolTable;
    }

    public VerbaASTNodeCollector allSubExpressions() {
        return this.allSubExpressions;
    }

    public void update() {
        this.symbolTable = new GlobalSymbolTable(this);
        this.allSubExpressions = new VerbaASTNodeCollector(this);
    }
}
