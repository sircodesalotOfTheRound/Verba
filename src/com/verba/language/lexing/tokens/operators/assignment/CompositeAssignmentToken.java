package com.verba.language.lexing.tokens.operators.assignment;

/**
 * Created by sircodesalot on 14-2-27.
 */
public abstract class CompositeAssignmentToken extends AssignmentToken {
    protected CompositeAssignmentToken(String representation) {
        super(representation);
    }
}
