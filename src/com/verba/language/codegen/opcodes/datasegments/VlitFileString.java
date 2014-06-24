package com.verba.language.codegen.opcodes.datasegments;

/**
 * Created by sircodesalot on 14-4-28.
 */
public class VlitFileString {
    public String representation;

    public VlitFileString(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return this.representation;
    }

    public void render(DataSegment segment) {
        segment.addString(representation);
    }
}
