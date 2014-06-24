package com.verba.language.codegen.opcodes.opcodes.math;

import com.verba.language.codegen.opcodes.VMOp;
import com.verba.language.codegen.opcodes.datasegments.DataSegment;
import com.verba.language.codegen.opcodes.datasegments.OpCode;

/**
 * Created by sircodesalot on 14-4-29.
 */
public class SubOp implements OpCode {
    private final byte lregister;
    private final byte rregister;
    private final byte resultRegister;

    public SubOp(byte lhs, byte rhs, byte resultRegister) {
        this.lregister = lhs;
        this.rregister = rhs;
        this.resultRegister = resultRegister;
    }

    @Override
    public void render(DataSegment segment) {
        segment.addOpCode(VMOp.SUB);

        segment.add8(resultRegister);
        segment.add8(lregister);
        segment.add8(rregister);
    }

    @Override
    public VMOp opcode() {
        return VMOp.SUB;
    }
}
