package com.verba.language.codegen.opcodes.opcodes.call;

import com.verba.language.codegen.opcodes.VMOp;
import com.verba.language.codegen.opcodes.datasegments.DataSegment;
import com.verba.language.codegen.opcodes.datasegments.OpCode;

/**
 * Created by sircodesalot on 14-5-2.
 */
public class RetOp implements OpCode {
    @Override
    public VMOp opcode() {
        return VMOp.RET;
    }

    @Override
    public void render(DataSegment segment) {
        segment.addOpCode(this.opcode());
    }
}
