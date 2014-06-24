package com.verba.language.codegen.functions;

import com.javalinq.implementations.QList;
import com.verba.language.codegen.functions.statements.ReturnStatementOpEmitter;
import com.verba.language.codegen.functions.statements.VarNameOpEmitter;
import com.verba.language.codegen.opcodes.datasegments.DataSegment;
import com.verba.language.codegen.opcodes.emit.OpEmitter;
import com.verba.language.codegen.opcodes.emit.VLitEmitter;
import com.verba.language.exceptions.CompilerException;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.statements.returns.ReturnStatementExpression;
import com.verba.language.symbols.meta.decorators.FunctionSymbol;
import com.verba.language.symbols.table.entries.SymbolTableEntry;

/**
 * Lays out the opcodes for a particular function.
 */
public class VlitFunctionGenerator implements VLitEmitter {
    //private final DataSegment segment;
    private final FunctionSymbol function;
    private final VlitFunctionDeclaration header;
    private final QList<OpEmitter> opcodeEmitters = new QList<>();

    public VlitFunctionGenerator(SymbolTableEntry entry) {
        this.function = new FunctionSymbol(entry);
        this.header = new VlitFunctionDeclaration(function);

        this.processFunction();
    }

    private void processFunction() {
        int startingRegister = 0;

        for (VerbaExpression expression : this.function.block()) {
            OpEmitter emitter;

            // Return statement
            if (expression instanceof ReturnStatementExpression)
                emitter = new ReturnStatementOpEmitter((ReturnStatementExpression) expression);

            // VarName Declaration / Method Call
            else if (expression instanceof VarNameDeclarationExpression)
                emitter = new VarNameOpEmitter(startingRegister, (VarNameDeclarationExpression) expression);

            // Otherwise exception
            else
                throw new CompilerException("Unable to emit code for type");

            this.opcodeEmitters.add(emitter);
        }
    }

    public VlitFunctionDeclaration declaration() {
        return this.header;
    }

    public void emit(DataSegment segment) {
        this.header.emit(segment);

        DataSegment opcodes = new DataSegment();
        for (OpEmitter emitter : this.opcodeEmitters) {
            emitter.emit(opcodes);
        }

        // Add the length of the code segment
        segment.add32((int)opcodes.count());

        // Write the code segment to the main segment
        segment.appendSegment(opcodes);
    }

}
