package com.verba.language.codegen.opcodes.output.debug;

/**
 * Created by sircodesalot on 14-4-28.
 */
public class StdOutRenderer {
   // private final Mode mode;
    //private final VlitRenderable renderable;

   // public void project() {
   //     this.renderable.render(this);
   // }

    public enum Mode {
        DECIMAL,
        HEX
    }
/*
    public StdOutRenderer(VlitRenderable renderable, Mode mode) {
        this.renderable = renderable;
        this.mode = mode;
    }

    @Override
    public void render(byte[] data) {
        for (byte b : data) this.render8(b);
    }

    @Override
    public void render8(byte data) {
        switch (mode) {
            case DECIMAL:
                System.out.print(String.format("%s ", (int) data));
                break;
            case HEX:
                System.out.print(String.format("%02x ", data));
                break;
        }
    }*/
}
