package com.verba.language.parse.expressions.codepage;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.javalinq.tools.Partition;
import com.verba.language.build.configuration.Build;
import com.verba.language.emit.variables.VirtualVariable;
import com.verba.language.graph.expressions.functions.FunctionGraphVisitor;
import com.verba.language.graph.symbols.table.tables.Scope;
import com.verba.language.graph.symbols.table.tables.SymbolTable;
import com.verba.language.graph.tools.ExpressionTreeFlattener;
import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.codestream.FileBasedCodeStream;
import com.verba.language.parse.expressions.VerbaExpression;
import com.verba.language.parse.expressions.categories.ExpressionSource;
import com.verba.language.parse.expressions.categories.SymbolTableExpression;
import com.verba.language.parse.lexing.Lexer;
import com.verba.language.parse.lexing.VerbaMemoizingLexer;
import com.verba.language.tools.ImportedNamespaceSet;
import org.apache.commons.codec.digest.DigestUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.InputStream;

/**
 * A Codepage is a page of Verba Code.
 */
public class VerbaSourceCodeFile extends VerbaExpression implements SymbolTableExpression, ExpressionSource
{
  private QList<VerbaExpression> childExpressions;
  private QIterable<VerbaExpression> allExpressions;
  private Partition<Class, VerbaExpression> expressionsByType;
  private ImportedNamespaceSet namespaces;
  private String path;
  private String text;
  private String hash;

  private VerbaSourceCodeFile(VerbaExpression parent, Lexer lexer) {
    super(parent, lexer);

    this.path = lexer.filename();
    this.text = lexer.text();
    this.hash = DigestUtils.sha1Hex(text);
    this.childExpressions = this.readChildExpressions(lexer);
    this.allExpressions = captureAllExpressions(this.childExpressions);
    this.expressionsByType = this.allExpressions.parition(Object::getClass);
  }

  @Override
  protected void onChildRemoved(VerbaExpression child) {

  }

  @Override
  public void afterContentsParsed(Build build) {
    this.namespaces = new ImportedNamespaceSet(this);
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

  private QIterable<VerbaExpression> captureAllExpressions(QList<VerbaExpression> childExpressions) {
    QList<VerbaExpression> allExpressions = new QList<>();

    for (VerbaExpression expression : childExpressions) {
      allExpressions.add(new ExpressionTreeFlattener(expression).expressions());
    }

    return allExpressions;
  }

  private QList<VerbaExpression> readChildExpressions(Lexer lexer) {
    QList<VerbaExpression> expressionList = new QList<>();

    while (lexer.notEOF()) {
      VerbaExpression expression = VerbaExpression.read(this, lexer);
      expressionList.add(expression);
    }

    return expressionList;
  }

  public static VerbaSourceCodeFile read(VerbaExpression parent, Lexer lexer) {
    return new VerbaSourceCodeFile(parent, lexer);
  }

  public QIterable<VerbaExpression> allExpressions() { return this.allExpressions; }

  public QIterable<VerbaExpression> childExpressions() { return this.childExpressions; }

  public <T> QIterable<T> expressionsByType(Class<T> type) {
    if (this.expressionsByType.containsKey(type)) {
      return this.expressionsByType.get(type).cast(type);
    } else {
      return new QList<>();
    }
  }

  public String text() { return this.text; }
  public String path() { return this.path; }
  public String hash() { return this.hash; }

  // Build from a file path.
  public static VerbaSourceCodeFile fromFile(VerbaExpression parent, String path) {
    CodeStream codeStream = new FileBasedCodeStream(path);
    Lexer lexer = new VerbaMemoizingLexer(path, codeStream);

    return new VerbaSourceCodeFile(parent, lexer);
  }

  // Build from an item in a package.
  public static VerbaSourceCodeFile fromResourceStream(String path) {
    try {
      StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      Class packageClass = Class.forName(stackTrace[stackTrace.length - 1].getClassName());

      InputStream stream = packageClass.getResourceAsStream(path);
      CodeStream codeStream = new FileBasedCodeStream(path, stream);
      Lexer lexer = new VerbaMemoizingLexer(path, codeStream);

      return new VerbaSourceCodeFile(null, lexer);
    } catch (Exception ex) {
      throw new NotImplementedException();
    }
  }

  public QIterable<String> importedNamespaces() { return this.namespaces; }

  @Override
  public void accept(ExpressionTreeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void accept(Scope symbolTable) {
    symbolTable.visit(this);
  }

  @Override
  public VirtualVariable accept(FunctionGraphVisitor visitor) {
    return null;
  }
}
