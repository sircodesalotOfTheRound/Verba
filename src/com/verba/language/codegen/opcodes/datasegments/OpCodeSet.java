package com.verba.language.codegen.opcodes.datasegments;


import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-4-28.
 */
public class OpCodeSet implements QIterable<OpCode> {
    private final QList<OpCode> opcodes = new QList<>();

    public void add(OpCode code) {
        this.opcodes.add(code);
    }

    @Override
    public Iterator<OpCode> iterator() {
        return this.opcodes.iterator();
    }
}
