package com.verba.language.codegen.functions.statements;

import com.verba.language.codegen.opcodes.datasegments.DataSegment;
import com.verba.language.codegen.opcodes.emit.OpEmitter;
import com.verba.language.codegen.opcodes.opcodes.call.RetOp;
import com.verba.language.expressions.statements.returns.ReturnStatementExpression;

/**
 * Created by sircodesalot on 14-6-11.
 */
public class ReturnStatementOpEmitter extends OpEmitter {
    private final ReturnStatementExpression retStatement;

    public ReturnStatementOpEmitter(ReturnStatementExpression retStatement) {
        super(0);
        this.retStatement = retStatement;
    }


    @Override
    public void emit(DataSegment segment) {
        segment.addOpCode(new RetOp());
    }
}
