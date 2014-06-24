package com.verba.language.codegen.opcodes.datasegments;

import com.verba.language.codegen.opcodes.VMOp;

/**
 * Created by sircodesalot on 14-4-28.
 */
public interface OpCode {
    VMOp opcode();
    void render(DataSegment segment);
}
