package com.verba.test.parsing.containers;

import com.verba.language.expressions.blockheader.varname.VarNameDeclarationExpression;
import com.verba.language.expressions.categories.RValueExpression;
import com.verba.language.expressions.containers.markup.MarkupDeclarationExpression;
import com.verba.language.expressions.containers.markup.MarkupTagItemExpression;
import com.verba.language.lexing.Lexer;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-21.
 */
public class TagContainerParsing {

    @Test
    public void testTagContainerParsing() {
        Lexer lexer = GeneralLexing.generateLexerFromString("<Something key=value second=2>");
        MarkupDeclarationExpression expression =
            (MarkupDeclarationExpression) RValueExpression.read(null, lexer);
        MarkupTagItemExpression tag = (MarkupTagItemExpression) expression.items().single();

        assert (tag.identifier() instanceof VarNameDeclarationExpression);

        VarNameDeclarationExpression identifier = (VarNameDeclarationExpression) tag.identifier();
        assert (identifier.representation().equals("Something"));

        assert (tag.items().count() == 2);
        assert (!tag.isSelfClosing());
        assert (!tag.isClosingTag());
    }

    @Test
    public void testClosingTag() {
        Lexer lexer = GeneralLexing.generateLexerFromString("</Something key=value second=2>");
        MarkupDeclarationExpression expression = (MarkupDeclarationExpression) RValueExpression.read(null, lexer);
        MarkupTagItemExpression tag = (MarkupTagItemExpression) expression.items().single();

        assert (tag.identifier() instanceof VarNameDeclarationExpression);

        VarNameDeclarationExpression identifier = (VarNameDeclarationExpression) tag.identifier();
        assert (identifier.representation().equals("Something"));

        assert (tag.items().count() == 2);
        assert (!tag.isSelfClosing());
        assert (tag.isClosingTag());
    }

    @Test
    public void testSelfClosingTag() {
        Lexer lexer = GeneralLexing.generateLexerFromString("<Instance key=value second=2 />");
        MarkupDeclarationExpression expression = (MarkupDeclarationExpression) RValueExpression.read(null, lexer);
        MarkupTagItemExpression tag = (MarkupTagItemExpression) expression.items().single();

        assert (tag.identifier() instanceof VarNameDeclarationExpression);

        VarNameDeclarationExpression identifier = (VarNameDeclarationExpression) tag.identifier();
        assert (identifier.representation().equals("Instance"));

        assert (tag.items().count() == 2);
        assert (tag.isSelfClosing()); // Breaks because it thinks this is an RpnValue expression '/'.
        assert (!tag.isClosingTag());
    }


    @Test
    public void embeddedTagTesting() {
        Lexer lexer = GeneralLexing.generateLexerFromString("<Instance key=value second=2 />");
        MarkupDeclarationExpression expression = (MarkupDeclarationExpression) RValueExpression.read(null, lexer);
        MarkupTagItemExpression tag = (MarkupTagItemExpression) expression.items().single();

        assert (tag.identifier() instanceof VarNameDeclarationExpression);

        VarNameDeclarationExpression identifier = (VarNameDeclarationExpression) tag.identifier();
        assert (identifier.representation().equals("Instance"));

        assert (tag.items().count() == 2);
        assert (tag.isSelfClosing()); // Breaks because it thinks this is an RpnValue expression '/'.
        assert (!tag.isClosingTag());
    }

}
