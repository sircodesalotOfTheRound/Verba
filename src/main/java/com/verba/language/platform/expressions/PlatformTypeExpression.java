package com.verba.language.platform.expressions;

import com.javalinq.implementations.QSet;
import com.javalinq.tools.Partition;
import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.TypeConstraintExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.tokens.identifiers.KeywordToken;
import com.verba.tools.exceptions.CompilerException;

/**
 * Created by sircodesalot on 15/3/17.
 */
public class PlatformTypeExpression extends VerbaExpression implements TypeConstraintExpression {
  public static final PlatformTypeExpression UNIT = new PlatformTypeExpression(KeywordToken.UNIT);
  public static final PlatformTypeExpression DYNAMIC = new PlatformTypeExpression(KeywordToken.DYNAMIC);
  public static final PlatformTypeExpression OBJECT = new PlatformTypeExpression(KeywordToken.OBJECT);
  public static final PlatformTypeExpression JSON = new PlatformTypeExpression(KeywordToken.JSON);

  public static final PlatformTypeExpression BOOLEAN = new PlatformTypeExpression(KeywordToken.BOOLEAN);
  public static final PlatformTypeExpression POLY = new PlatformTypeExpression(KeywordToken.POLY);
  public static final PlatformTypeExpression MATRIX = new PlatformTypeExpression(KeywordToken.MATRIX);

  public static final PlatformTypeExpression NUMERIC = new PlatformTypeExpression(KeywordToken.NUMERIC);
  public static final PlatformTypeExpression BYTE = new PlatformTypeExpression(KeywordToken.BYTE);

  public static final PlatformTypeExpression INT = new PlatformTypeExpression(KeywordToken.INT);
  public static final PlatformTypeExpression INT8 = new PlatformTypeExpression(KeywordToken.INT8);
  public static final PlatformTypeExpression INT16 = new PlatformTypeExpression(KeywordToken.INT16);
  public static final PlatformTypeExpression INT32 = new PlatformTypeExpression(KeywordToken.INT32);
  public static final PlatformTypeExpression INT64 = new PlatformTypeExpression(KeywordToken.INT64);

  public static final PlatformTypeExpression UINT = new PlatformTypeExpression(KeywordToken.UINT);
  public static final PlatformTypeExpression UINT8 = new PlatformTypeExpression(KeywordToken.UINT8);
  public static final PlatformTypeExpression UINT16 = new PlatformTypeExpression(KeywordToken.UINT16);
  public static final PlatformTypeExpression UINT32 = new PlatformTypeExpression(KeywordToken.UINT32);
  public static final PlatformTypeExpression UINT64 = new PlatformTypeExpression(KeywordToken.UINT64);

  public static final PlatformTypeExpression DECIMAL = new PlatformTypeExpression(KeywordToken.DECIMAL);
  public static final PlatformTypeExpression DECIMAL32 = new PlatformTypeExpression(KeywordToken.DECIMAL32);
  public static final PlatformTypeExpression DECIMAL64 = new PlatformTypeExpression(KeywordToken.DECIMAL64);

  public static final PlatformTypeExpression CURRENCY = new PlatformTypeExpression(KeywordToken.CURRENCY);

  public static final PlatformTypeExpression CHAR = new PlatformTypeExpression(KeywordToken.CHAR);

  public static final PlatformTypeExpression ASCII = new PlatformTypeExpression(KeywordToken.ASCII);
  public static final PlatformTypeExpression UTF = new PlatformTypeExpression(KeywordToken.UTF);
  public static final PlatformTypeExpression UTF8 = new PlatformTypeExpression(KeywordToken.UTF8);
  public static final PlatformTypeExpression UTF8BE = new PlatformTypeExpression(KeywordToken.UTF8BE);
  public static final PlatformTypeExpression UTF16 = new PlatformTypeExpression(KeywordToken.UTF16);
  public static final PlatformTypeExpression UTF16BE = new PlatformTypeExpression(KeywordToken.UTF16BE);
  public static final PlatformTypeExpression UTF32 = new PlatformTypeExpression(KeywordToken.UTF32);
  public static final PlatformTypeExpression UTF32BE = new PlatformTypeExpression(KeywordToken.UTF32BE);

  private static final QSet<PlatformTypeExpression> validPrimitiveTypes = new QSet<>(
    UNIT, DYNAMIC, OBJECT, JSON,
    BOOLEAN, POLY, MATRIX,
    NUMERIC, BYTE,

    INT, INT8, INT16, INT32, INT64,
    UINT, UINT8, UINT16, UINT32, UINT64,

    DECIMAL, DECIMAL32, DECIMAL64,
    CURRENCY,

    CHAR,
    ASCII, UTF,
    UTF8, UTF8BE, UTF16, UTF16BE, UTF32, UTF32BE
  );

  private static final Partition<String, PlatformTypeExpression> expressionsByName
    = validPrimitiveTypes.parition(PlatformTypeExpression::type);

  private final String type;

  public PlatformTypeExpression(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);
    this.type = validateType(lexer);
  }

  private PlatformTypeExpression(String type) {
    super(PlatformSourceExpression.INSTANCE, null);
    this.type = type;
  }


  private String validateType(Lexer lexer) {
    if (isPlatformTypeName(lexer)) {
      return lexer.readCurrentAndAdvance().representation();
    } else {
      throw new CompilerException("Invalid Platform Type");
    }
  }

  public String type() { return this.type; }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void afterContentsParsed(Build build) {

  }

  @Override
  public void afterSymbolsGenerated(Build build, SymbolTable table) {

  }

  @Override
  public void onResolveSymbols(Build build, SymbolTable table) {

  }

  @Override
  public void onValidate(Build build, SymbolTable table) {

  }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {

  }

  public static boolean isPlatformTypeName(String name) {
    return expressionsByName.containsKey(name);
  }

  public static boolean isPlatformTypeName(Lexer lexer) {
    if (lexer.isEOF()) {
      return false;
    }

    String representation = lexer.current().representation();
    return isPlatformTypeName(representation);
  }

  public static PlatformTypeExpression read(VerbaExpression parent, Lexer lexer) {
    return new PlatformTypeExpression(parent, lexer);
  }

  @Override
  public String representation() { return this.type; }

  @Override
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return null;
  }
}
