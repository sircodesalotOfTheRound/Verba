package com.verba.language.build.codepage;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.categories.SymbolTableExpression;
import com.verba.language.lexing.Lexer;
import com.verba.language.lexing.VerbaMemoizingLexer;
import com.verba.language.lexing.codestream.CodeStream;
import com.verba.language.lexing.codestream.FileBasedCodeStream;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.InputStream;

/**
 * A Codepage is a page of Verba Code.
 */
public class VerbaCodePage extends VerbaExpression implements SymbolTableExpression {
    private QList<VerbaExpression> expressions = new QList<>();
    private String path;

    private VerbaCodePage(VerbaExpression parent, Lexer lexer) {
        super(parent, lexer);

        this.path = lexer.filename();
        this.expressions = this.readExpressions(lexer);
    }

    private QList<VerbaExpression> readExpressions(Lexer lexer) {
        QList<VerbaExpression> expressionList = new QList<>();

        while (lexer.notEOF()) {
            VerbaExpression expression = VerbaExpression.read(this, lexer);
            expressionList.add(expression);
        }

        return expressionList;
    }

    public static VerbaCodePage read(VerbaExpression parent, Lexer lexer) {
        throw new NotImplementedException();
    }

    public QIterable<VerbaExpression> expressions() {
        return this.expressions;
    }

    public String path() {
        return this.path;
    }

    // Build from a file path.
    public static VerbaCodePage fromFile(VerbaExpression parent, String path) {
        CodeStream codeStream = new FileBasedCodeStream(path);
        Lexer lexer = new VerbaMemoizingLexer(path, codeStream);

        return new VerbaCodePage(parent, lexer);
    }

    // Build from an item in a package.
    public static VerbaCodePage fromPackageItem(VerbaExpression parent, Class packageClass, String path) {
        InputStream stream = packageClass.getResourceAsStream(path);
        CodeStream codeStream = new FileBasedCodeStream(path, stream);
        Lexer lexer = new VerbaMemoizingLexer(path, codeStream);

        return new VerbaCodePage(parent, lexer);
    }
}
