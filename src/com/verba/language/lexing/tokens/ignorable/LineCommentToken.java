package com.verba.language.lexing.tokens.ignorable;

import com.verba.language.lexing.codestream.CodeStream;
import com.verba.language.lexing.tokenization.Token;

/**
 * Created by sircodesalot on 14-5-20.
 */
public class LineCommentToken implements Token {
    private final String representation;

    public LineCommentToken(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return this.representation;
    }

    public static boolean isLineCommentToken(CodeStream stream) {
        boolean isComment = false;

        stream.setUndoPosition();
        if (stream.read() == '#') {
            if (stream.hasNext() && (stream.read() != '[')) {
                isComment = true;
            }
        }

        stream.rollbackToUndoPosition();
        return isComment;
    }

    public static LineCommentToken read(CodeStream codeStream) {
        StringBuilder representation = new StringBuilder();
        int line = codeStream.line();

        do {
            representation.append(codeStream.read());
        } while (codeStream.hasNext() && codeStream.line() == line);

        return new LineCommentToken(representation.toString());
    }
}
