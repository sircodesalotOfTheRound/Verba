package com.verba.test.tools;

import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.VerbaMemoizingLexer;
import com.verba.language.lexing.codestream.CodeStream;
import com.verba.language.lexing.codestream.FileBasedCodeStream;
import com.verba.language.lexing.codestream.StringBasedCodeStream;

/**
 * Created by sircodesalot on 14-3-21.
 */
public class GeneralLexing {
    public static Lexer generateLexerFromString(String code) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return generateLexerFromString(stackTrace[2].getMethodName(), code);
    }

    public static Lexer generateLexerFromString(String filename, String code) {
        return GeneralLexing.generateLexerFromString(filename, code, false);
    }

    public static Lexer generateLexerFromString(String filename, String code, boolean includeWhitespace) {
        CodeStream stream = new StringBasedCodeStream(filename, code);
        return new VerbaMemoizingLexer(filename, stream, includeWhitespace, false);
    }


    public static Lexer generateFromFileInThisPackage(String filename) throws Exception {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        Class<?> aClass = Class.forName(className);
        CodeStream stream = new FileBasedCodeStream(filename, aClass.getResourceAsStream(filename));

        return new VerbaMemoizingLexer(filename, stream, false, false);
    }
}
