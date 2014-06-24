package com.verba.language.codegen.opcodes.datasegments;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.codegen.opcodes.VMOp;

import java.io.FileOutputStream;
import java.util.Iterator;

/**
 * Created by sircodesalot on 14-4-28.
 */
public class DataSegment implements QIterable<Byte> {
    private final QList<Byte> data;

    public DataSegment(Byte... data) {
        this.data = new QList<>(data);
    }

    public void debugPrint() {
        int lineLength = 0;

        for (byte data : this) {
            if (data >= '!' && data <= 'z')
                System.out.print(String.format("%s(%s) ", Integer.toHexString(data), (char) data));
            else
                System.out.print(String.format("%s ", Integer.toHexString(data)));

            if (++lineLength % 20 == 0) System.out.println();
        }
    }

    public void add8(byte value) {
        this.data.add(value);
    }

    public void add16(short value) {
        byte first = (byte) (value & (0xFF));
        byte second = (byte) ((value >> 8) & (0xFF));

        this.add8(first);
        this.add8(second);
    }

    public void add32(int value) {
        byte first = (byte) (value & (0xFF));
        byte second = (byte) ((value >> 8) & (0xFF));
        byte third = (byte) ((value >> 16) & (0xFF));
        byte fourth = (byte) ((value >> 24) & (0xFF));

        this.data.add(first);
        this.data.add(second);
        this.data.add(third);
        this.data.add(fourth);
    }

    public void add64(long value) {
        byte first = (byte) (value & (0xFF));
        byte second = (byte) ((value >> 8) & (0xFF));
        byte third = (byte) ((value >> 16) & (0xFF));
        byte fourth = (byte) ((value >> 24) & (0xFF));
        byte fifth = (byte) ((value >> 32) & (0xFF));
        byte sixth = (byte) ((value >> 40) & (0xFF));
        byte seventh = (byte) ((value >> 48) & (0xFF));
        byte eighth = (byte) ((value >> 56) & (0xFF));

        this.data.add(first);
        this.data.add(second);
        this.data.add(third);
        this.data.add(fourth);
        this.data.add(fifth);
        this.data.add(sixth);
        this.data.add(seventh);
        this.data.add(eighth);
    }

    public void addOpCode(VMOp opcode) {
        this.data.add((byte) opcode.asInt());
    }

    public void addOpCode(OpCode opcode) {
        opcode.render(this);
    }

    public void addString(String string) {
        this.add16((short) string.length());

        for (int index = 0; index < string.length(); index++) {
            this.data.add((byte) string.charAt(index));
        }
    }

    public void appendSegment(DataSegment data) {
        for (byte item : data) this.data.add(item);
    }

    @Override
    public long count() {
        return this.data.count();
    }

    @Override
    public Iterator<Byte> iterator() {
        return this.data.iterator();
    }

    public void save(String path) {
        try {
            FileOutputStream stream = new FileOutputStream(path);

            for (byte data : this) {
                stream.write(data);
            }

            stream.close();

        } catch (Exception ex) { }
    }


}
