package com.verba.test.tools;

import com.javalinq.interfaces.QIterable;
import com.verba.language.ast.VerbaASTNodeCollector;
import com.verba.language.expressions.VerbaExpression;
import com.verba.language.lexing.Lexer;

/**
 * Created by sircodesalot on 14-4-25.
 */
public class AstTools {
    public static QIterable<VerbaExpression> generateAstCollectionFromString(String code) {
        Lexer lexer = GeneralLexing.generateLexerFromString(code);
        VerbaExpression root = VerbaExpression.read(null, lexer);

        return new VerbaASTNodeCollector(root).expressions();
    }
}
