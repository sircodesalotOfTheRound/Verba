package com.verba.test.validation;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.validation.fqn.FullyQualifiedNameValidator;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-5.
 */
public class FQNValidation {
    @Test
    public void validateSimpleFQN() {
        Lexer lexer = GeneralLexing.generateLexerFromString("First.Second.Third.Fourth");
        VarNameDeclarationExpression fqn = (VarNameDeclarationExpression) VerbaExpression.read(null, lexer);
        FullyQualifiedNameValidator validator = new FullyQualifiedNameValidator(fqn.identifier());

        assert (!validator.hasParameters());
        assert (!validator.hasGenericParameters());
        assert (!validator.hasSingleMember());
    }

    @Test
    public void validateFQNWithParameters() {
        Lexer lexer = GeneralLexing.generateLexerFromString("First.Second(1).Third.Fourth");
        VarNameDeclarationExpression fqn = (VarNameDeclarationExpression) VerbaExpression.read(null, lexer);
        FullyQualifiedNameValidator validator = new FullyQualifiedNameValidator(fqn.identifier());

        assert (validator.hasParameters());
        assert (!validator.hasGenericParameters());
        assert (!validator.hasSingleMember());

        assert (validator.membersWithParameters().count() == 1);
        assert (validator.membersWithParameters().single().memberName().equals("Second"));
    }

}
