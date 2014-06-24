package com.verba.test.lexing;

import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.tokens.EnclosureToken;
import com.verba.language.lexing.tokens.identifiers.IdentifierToken;
import com.verba.language.lexing.tokens.identifiers.KeywordToken;
import com.verba.language.lexing.tokens.ignorable.WhitespaceToken;
import com.verba.language.lexing.tokens.operators.OperatorToken;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-7.
 */
public class TokenLexing {
    @Test
    public void lexClassTokensWithoutWhitespace() {
        Lexer lexer = GeneralLexing.generateLexerFromString("PraseClassTokensWithoutWhitespace", "class MyClass<T> { }");

        lexer.readCurrentAndAdvance(KeywordToken.class, "class");
        lexer.readCurrentAndAdvance(IdentifierToken.class, "MyClass");
        lexer.readCurrentAndAdvance(OperatorToken.class, "<");
        lexer.readCurrentAndAdvance(IdentifierToken.class, "T");
        lexer.readCurrentAndAdvance(OperatorToken.class, ">");
        lexer.readCurrentAndAdvance(EnclosureToken.class, "{");
        lexer.readCurrentAndAdvance(EnclosureToken.class, "}");

        assert (lexer.isEOF());
    }

    @Test
    public void lexClassTokensWithWhitespace() {
        Lexer lexer = GeneralLexing.generateLexerFromString("PaseClassTokensWithWhitespace", "class MyClass<T> { }", true);

        lexer.readCurrentAndAdvance(KeywordToken.class, "class");
        lexer.readCurrentAndAdvance(WhitespaceToken.class);
        lexer.readCurrentAndAdvance(IdentifierToken.class, "MyClass");
        lexer.readCurrentAndAdvance(OperatorToken.class, "<");
        lexer.readCurrentAndAdvance(IdentifierToken.class, "T");
        lexer.readCurrentAndAdvance(OperatorToken.class, ">");
        lexer.readCurrentAndAdvance(WhitespaceToken.class);
        lexer.readCurrentAndAdvance(EnclosureToken.class, "{");
        lexer.readCurrentAndAdvance(WhitespaceToken.class);
        lexer.readCurrentAndAdvance(EnclosureToken.class, "}");

        assert (lexer.isEOF());
    }
}
