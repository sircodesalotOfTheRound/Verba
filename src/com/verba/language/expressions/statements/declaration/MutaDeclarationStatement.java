package com.verba.language.expressions.statements.declaration;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.categories.AssignmentExpression;
import com.verba.language.expressions.categories.NamedDataDeclarationExpression;
import com.verba.language.expressions.categories.RValueExpression;
import com.verba.language.expressions.categories.TypeDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;
import com.verba.language.lexing.tokens.operators.assignment.AssignmentToken;

/**
 * Created by sircodesalot on 14-2-19.
 */
public class MutaDeclarationStatement extends VerbaExpression
    implements NamedDataDeclarationExpression, AssignmentExpression {

    private VarNameDeclarationExpression identifier;
    private RValueExpression rvalue;

    private MutaDeclarationStatement(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        this.readExpression(lexer);
    }

    private void readExpression(Lexer lexer) {
        lexer.readCurrentAndAdvance(KeywordToken.class, "muta");
        this.identifier = VarNameDeclarationExpression.read(this, lexer);

        if (lexer.currentIs(AssignmentToken.class)) {
            lexer.readCurrentAndAdvance(AssignmentToken.class, "=");
            this.rvalue = RValueExpression.read(this, lexer);
        }
    }

    public static MutaDeclarationStatement read(VerbaExpression parent, Lexer lexer) {
        return new MutaDeclarationStatement(parent, lexer);
    }


    @Override
    public boolean hasTypeConstraint() {
        return this.identifier.hasTypeConstraint();
    }

    @Override
    public String name() {
        return this.identifier.name();
    }

    @Override
    public TypeDeclarationExpression typeDeclaration() {
        return this.identifier.typeDeclaration();
    }

    public VarNameDeclarationExpression identifier() {
        return this.identifier;
    }

    @Override
    public boolean hasRValue() {
        return this.rvalue != null;
    }

    @Override
    public RValueExpression rvalue() {
        return this.rvalue;
    }
}
