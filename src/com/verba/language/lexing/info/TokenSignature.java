package com.verba.language.lexing.info;

/**
 * Created by sircodesalot on 14-2-21.
 */
public class TokenSignature {
    private Class type;
    private String representation;

    private TokenSignature(Class type, String representation) {
        this.type = type;
        this.representation = representation;
    }

    public boolean tokenMatchesThisSignature(LexInfo token) {
        if (this.type != null && this.representation != null) return token.is(type, representation);
        else if (this.type != null) return token.is(type);
        else if (this.representation != null) return token.is(representation);

        return false;
    }

    public Class getType() {
        return type;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public String toString() {
        if (this.type != null && this.representation != null)
            return String.format("%s : %s", this.type, this.representation);
        else if (this.type != null) return String.format("%s", this.type);
        else return String.format("%s", this.representation);
    }

    public static TokenSignature make(Class type, String representation) {
        return new TokenSignature(type, representation);
    }

    public static TokenSignature make(String representation) {
        return new TokenSignature(null, representation);
    }

    public static TokenSignature make(Class type) {
        return new TokenSignature(type, null);
    }
}
