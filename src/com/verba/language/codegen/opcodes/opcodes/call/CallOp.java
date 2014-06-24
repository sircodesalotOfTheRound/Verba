package com.verba.language.codegen.opcodes.opcodes.call;

import com.verba.language.codegen.opcodes.VMOp;
import com.verba.language.codegen.opcodes.datasegments.DataSegment;
import com.verba.language.codegen.opcodes.datasegments.OpCode;

/**
 * Created by sircodesalot on 14-5-2.
 */
public class CallOp implements OpCode {
    private final String name;
    private int startRegister;

    public CallOp(int startRegister, String name) {
        this.startRegister = startRegister;
        this.name = name;
    }

    @Override
    public VMOp opcode() {
        return VMOp.CALL;
    }

    @Override
    public void render(DataSegment segment) {
        segment.addOpCode(this.opcode());
        segment.add8((byte) this.startRegister);
        segment.addString(name);
    }
}
