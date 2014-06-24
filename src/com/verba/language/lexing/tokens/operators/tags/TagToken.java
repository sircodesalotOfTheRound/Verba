package com.verba.language.lexing.tokens.operators.tags;

import com.verba.language.exceptions.ParseException;
import com.verba.language.lexing.codestream.CodeStream;
import com.verba.language.lexing.tokens.operators.OperatorToken;

/**
 * Created by sircodesalot on 14-2-28.
 */
public abstract class TagToken extends OperatorToken {
    private String representation;

    protected TagToken(String representation) {
        super('t');
        this.representation = representation;
    }

    public String toString() {
        return this.representation;
    }

    public static boolean isTagToken(Character firstToken, CodeStream stream) {
        if (firstToken == '#' && stream.peek() == '[') return true;
        else if (firstToken == '@' && stream.peek() == '[') return true;

        else return false;
    }

    public static OperatorToken read(Character firstToken, CodeStream stream) {
        if (firstToken == '#' && stream.peek() == '[') {
            stream.read();
            return new HashTagToken();
        } else if (firstToken == '@' && stream.peek() == '[') {
            stream.read();
            return new AspectTagToken();
        } else throw new ParseException("Expected Hashtag Token");
    }
}
