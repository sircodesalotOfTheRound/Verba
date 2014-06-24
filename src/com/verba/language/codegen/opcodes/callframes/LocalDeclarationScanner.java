package com.verba.language.codegen.opcodes.callframes;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.symbols.meta.decorators.FunctionSymbol;

/**
 * Created by sircodesalot on 14-6-11.
 */
public class LocalDeclarationScanner {
    private final FunctionSymbol function;
    private final QList<VerbaExpression> locals = new QList<>();

    public LocalDeclarationScanner(FunctionSymbol function) {
        this.function = function;

        this.scan();
    }

    private void scan() {
        // Add local variable name declarations
        for (VerbaExpression expression : function.block()) {
            if (expression instanceof VarNameDeclarationExpression)
                this.locals.add(expression);
        }
    }


    public QIterable<VerbaExpression> locals() { return this.locals; }
    public int count() { return (int) this.locals.count(); }
}
