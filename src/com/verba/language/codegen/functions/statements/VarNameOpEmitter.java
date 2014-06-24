package com.verba.language.codegen.functions.statements;

import com.verba.language.codegen.opcodes.datasegments.DataSegment;
import com.verba.language.codegen.opcodes.emit.OpEmitter;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.members.MemberExpression;

/**
 * Created by sircodesalot on 14-6-11.
 */
public class VarNameOpEmitter extends OpEmitter {
    private final VarNameDeclarationExpression declaration;

    public VarNameOpEmitter(int startingRegister, VarNameDeclarationExpression declaration) {
        super(startingRegister);

        this.declaration = declaration;

        this.isMethodCall(declaration.identifier().first());
    }

    public void isMethodCall(MemberExpression expression) {
    }

    @Override
    public void emit(DataSegment segment) {

    }
}
