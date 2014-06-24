package com.verba.language.codegen.functions;

import com.verba.language.codegen.opcodes.callframes.CallFrame;
import com.verba.language.codegen.opcodes.datasegments.DataSegment;
import com.verba.language.codegen.opcodes.emit.VLitEmitter;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.symbols.meta.decorators.FunctionSymbol;

/**
 * Generates a function declaration for a vlit file.
 */
public class VlitFunctionDeclaration implements VLitEmitter {
    private final FunctionSymbol function;
    private final CallFrame callframe;

    public VlitFunctionDeclaration(FunctionSymbol function) {
        this.function = function;
        this.callframe = new CallFrame(function);
    }

    private void writeHeader(DataSegment segment) {
        // <Name> <Generic Parameter Count> <Parameter Count> <Return Type>
        segment.addString(function.fqn());
        segment.add8((byte) function.genericParameters().count());
        segment.add8((byte) function.parameters().count());
        segment.addString(function.returnType().representation());

        writeParameters(segment);
    }

    private void writeParameters(DataSegment segment) {

        for (VarNameDeclarationExpression parameter : function.parameters()
            .items().cast(VarNameDeclarationExpression.class)) {

            segment.addString(parameter.identifier().representation());
            segment.addString(parameter.typeDeclaration().representation());
        }
    }

    @Override
    public void emit(DataSegment segment) {
        writeHeader(segment);
    }
}
