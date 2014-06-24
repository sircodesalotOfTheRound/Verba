package com.verba.language.codegen.opcodes.callframes;

import com.verba.language.symbols.meta.decorators.FunctionSymbol;

/**
 * Created by sircodesalot on 14-6-11.
 */
public class CallFrame {
    private int workingRegister = 0;
    private FunctionSymbol function;
    private LocalDeclarationScanner scanner;

    public CallFrame(FunctionSymbol function) {
        this.function = function;
        this.scanner = new LocalDeclarationScanner(function);

        this.setWorkingRegister(scanner.count());
    }

    public int workingRegister() { return this.workingRegister; }

    private void setWorkingRegister(int workingRegister) { this.workingRegister = workingRegister; }

    public void incrementWorkingRegister() { this.workingRegister++; }
    public void decrementWorkingRegister() { this.workingRegister--; }
}
