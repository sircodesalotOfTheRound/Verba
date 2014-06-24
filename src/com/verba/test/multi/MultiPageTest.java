package com.verba.test.multi;

import com.verba.language.ast.VerbaASTNodeCollector;
import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.expressions.StaticSpaceExpression;
import com.verba.language.expressions.blockheader.classes.ClassDeclarationExpression;
import com.verba.language.expressions.blockheader.functions.FunctionDeclarationExpression;
import org.junit.Test;

/**
 * Created by sircodesalot on 14-5-19.
 */
public class MultiPageTest {
    @Test
    public void multiPageTest() {
        Class thisClass = this.getClass();
        VerbaCodePage page1 = VerbaCodePage.fromPackageItem(null, thisClass, "Page1.v");
        VerbaCodePage page2 = VerbaCodePage.fromPackageItem(null, thisClass, "Page2.v");

        StaticSpaceExpression staticSpaceExpression = new StaticSpaceExpression(page1, page2);
        VerbaASTNodeCollector spanningVisitor = staticSpaceExpression.allSubExpressions();

        assert (page1.expressions().count() == 2);
        assert (page2.expressions().count() == 2);
        assert (page1.expressions().count(expression -> expression instanceof ClassDeclarationExpression) == 1);
        assert (page2.expressions().count(expression -> expression instanceof FunctionDeclarationExpression) == 1);

        assert (staticSpaceExpression.rootLevelExpressions().count() == 2);

        assert (staticSpaceExpression.globalSymbolTable().entries().count() == 5);

        assert (staticSpaceExpression.globalSymbolTable().entries()
            .count(entry -> entry.instance() instanceof FunctionDeclarationExpression) == 2);

        assert (staticSpaceExpression.globalSymbolTable().entries()
            .count(entry -> entry.instance() instanceof ClassDeclarationExpression) == 3);

        // 5 Actual items + StaticSpace + 2 Files
        assert (spanningVisitor.expressions().count() == 8);
    }

}
