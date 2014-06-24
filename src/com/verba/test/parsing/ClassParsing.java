package com.verba.test.parsing;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.members.FullyQualifiedNameExpression;
import com.verba.language.expressions.members.MemberExpression;
import com.verba.language.lexing.Lexer;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-3-22.
 */
public class ClassParsing {
    @Test
    public void simpleClassTest() {
        Lexer lexer = GeneralLexing.generateLexerFromString("class MyClass { }");
        ClassDeclarationExpression declaration = (ClassDeclarationExpression) VerbaExpression.read(null, lexer);

        assert (!declaration.hasGenericParameters());
        assert (!declaration.isInlineClass());

        assert (declaration.name().equals("MyClass"));
    }

    @Test
    public void inlineClassTest() {
        Lexer lexer = GeneralLexing.generateLexerFromString("class MyClass(first : One, second : Two) { }");
        ClassDeclarationExpression declaration = (ClassDeclarationExpression) VerbaExpression.read(null, lexer);

        assert (!declaration.hasGenericParameters());
        assert (declaration.isInlineClass());

        /* TODO: Reimplement matches sequence somewhwere
        assert (declaration.inlineParameters().first()
                .items()
                .map(item -> ((VarNameDeclarationExpression) item).representation())
                .matchesSequence("first : One", "second : Two"));
        */

        assert (declaration.name().equals("MyClass"));
    }

    @Test
    public void polymorphicClassTest() {
        Lexer lexer = GeneralLexing.generateLexerFromString("class Derived<T>(first : One) : BaseClass<T> { }");
        ClassDeclarationExpression declaration = (ClassDeclarationExpression) VerbaExpression.read(null, lexer);

        assert (declaration.name().equals("Derived"));
        assert (declaration.genericParameters().first().representation().equals("T"));

        assert (declaration.inlineParameters()
            .single()
            .items()
            .singleAs(VarNameDeclarationExpression.class) // First parameter in this set
            .representation()
            .equals("first : One"));

        FullyQualifiedNameExpression baseClassRaw = (FullyQualifiedNameExpression) declaration.traits().first();
        MemberExpression baseClass = baseClassRaw.last();

        assert (baseClass.identifier().representation().equals("BaseClass"));
        assert (baseClass.hasGenericParameters());
        assert (!baseClass.hasParameters());
        assert (baseClass.genericParameterList().first().representation().equals("T"));
    }

}
