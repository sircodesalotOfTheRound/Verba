package com.verba.test.validation;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.validation.declarations.FunctionDeclarationValidator;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-3.
 */
public class FunctionValidation {
    @Test
    public void validateSimpleFunction() {
        Lexer lexer = GeneralLexing.generateLexerFromString("ValidateSimpleFunction",
            "fn myFunction(1, something, second : String) { }");

        FunctionDeclarationExpression function = (FunctionDeclarationExpression) VerbaExpression.read(null, lexer);
        FunctionDeclarationValidator validator = new FunctionDeclarationValidator(function);
        validator.validate();

        assert (validator.violations().single(violation ->
            violation.message().contains("is not a valid VarNameExpression")) != null);

        assert (validator.violations().single(violation ->
            violation.message().contains("The parameter 'something' must have a type constraint.")) != null);
    }
}
