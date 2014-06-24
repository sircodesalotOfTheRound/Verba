package com.verba.language.lexing.tokens;

import com.verba.language.lexing.tokens.operators.assignment.CompositeAssignmentToken;

/**
 * Created by sircodesalot on 14-2-26.
 */
public class PowerEqualsToken extends CompositeAssignmentToken {
    public PowerEqualsToken() {
        super("^=");
    }
}
