package com.verba.test.validation;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.validation.declarations.ClassDeclarationValidator;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-3.
 */
public class ClassValidation {
    @Test
    public void validateSimpleClass() {
        Lexer lexer = GeneralLexing.generateLexerFromString("class MyClass : Something<T : U>, Otherthing<T : String> { }");
        ClassDeclarationExpression declaration = (ClassDeclarationExpression) VerbaExpression.read(null, lexer);

        ClassDeclarationValidator validator = new ClassDeclarationValidator(declaration);
        validator.validate();

        assert (declaration.traits().count() == 2);

        String genericErrorMessage = "Generic Parameters in base types cannot be constained.";
        assert (validator.violations().all(violation -> violation.message().contains(genericErrorMessage)));
    }

    @Test
    public void validateInlineClass() {
        Lexer lexer = GeneralLexing.generateLexerFromString("class MyClass(something)(more, 2) { }");
        ClassDeclarationExpression declaration = (ClassDeclarationExpression) VerbaExpression.read(null, lexer);

        ClassDeclarationValidator validator = new ClassDeclarationValidator(declaration);
        validator.validate();

        assert (validator.violations().any(violation -> violation.message().contains("multiple sets of arguments")));
        assert (validator.violations().any(violation -> violation.message().contains("must be a variable declaration")));
    }


}
