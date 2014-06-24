package com.verba.language.codegen.opcodes.opcodes.math;

import com.verba.language.codegen.opcodes.VMOp;
import com.verba.language.codegen.opcodes.datasegments.DataSegment;
import com.verba.language.codegen.opcodes.datasegments.OpCode;
import com.verba.language.exceptions.CompilerException;
import com.verba.language.expressions.rvalue.simple.NumericExpression;

/**
 * Created by sircodesalot on 14-4-29.
 */
public class LoadIntOp implements OpCode {
    private final VMOp op;
    private final byte register;
    private final long value;

    private LoadIntOp(VMOp op, byte register, long value) {
        this.op = op;
        this.register = register;
        this.value = value;
    }

    public LoadIntOp(NumericExpression expression, byte register) {
        this(calculateVMOp(expression), register, expression.asLong());
    }

    private static VMOp calculateVMOp(NumericExpression expression) {
        NumericExpression.Size size = expression.size();
        switch (size) {
            case BYTE:
                return VMOp.LOADi1;
            case SHORT:
                return VMOp.LOADi2;
            case INTEGER:
                return VMOp.LOADi4;
            case LONG:
                return VMOp.LOADi8;

            default:
                throw new CompilerException("Invalid load command");
        }
    }

    @Override
    public VMOp opcode() {
        return op;
    }

    @Override
    public void render(DataSegment segment) {
        segment.addOpCode(op);
        segment.add8(register);

        switch (op) {
            case LOADi1:
                segment.add8((byte) value);
                break;
            case LOADi2:
                segment.add16((short) value);
                break;
            case LOADi4:
                segment.add32((int) value);
                break;
            case LOADi8:
                segment.add64(value);
                break;

            default:
                throw new CompilerException("Invalid load command");
        }
    }
}
