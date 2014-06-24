package com.verba.test.dependencies;

import com.verba.language.expressions.VerbaExpression;
import com.verba.language.expressions.dependencies.GrabExpression;
import com.verba.language.lexing.Lexer;
import com.verba.test.tools.GeneralLexing;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-20.
 */
public class DependencyTesting {
    @Test
    public void readDependencies() throws Exception {
        Lexer lexer = GeneralLexing.generateFromFileInThisPackage("Dependencies.v");

        GrabExpression first = (GrabExpression) VerbaExpression.read(null, lexer);
        GrabExpression second = (GrabExpression) VerbaExpression.read(null, lexer);

        assert (first.resourceNameAsString().equals("Afile.vlit"));
        assert (second.resourceNameAsString().equals("\"Another file in quotes.vlit\""));
    }

}
