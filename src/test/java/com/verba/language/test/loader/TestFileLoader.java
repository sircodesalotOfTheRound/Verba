package com.verba.language.test.loader;

import com.verba.language.build.codepage.VerbaCodePage;
import com.verba.language.parsing.expressions.StaticSpaceExpression;

/**
 * Created by sircodesalot on 14/9/9.
 */
public class TestFileLoader {
  public static final StaticSpaceExpression TYPE_SIGNATURE_TESTS =
    new StaticSpaceExpression(VerbaCodePage.fromResourceStream("/TypeSignatureTests.v"));

  public static final StaticSpaceExpression TYPE_RESOLUTION_TESTS =
    new StaticSpaceExpression(VerbaCodePage.fromResourceStream("/TypeResolutionTests.v"));

  public static final StaticSpaceExpression STATEMENT_TESTS =
    new StaticSpaceExpression(VerbaCodePage.fromResourceStream("/StatementTests.v"));
}
