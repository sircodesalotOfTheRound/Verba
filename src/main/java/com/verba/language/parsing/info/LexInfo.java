package com.verba.language.parsing.info;

import com.verba.language.parsing.tokenization.Token;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;

/**
 * Created by sircodesalot on 14-2-20.
 */
public class LexInfo implements Serializable {
  private Token token;
  private final String filename;
  private final int absolutePosition;
  private String typeName;

  // Can't serialize this, so we store the typename instead.
  private transient Class type;

  private final int line;
  private final int column;

  public LexInfo(Token token, TokenPosition position) {
    this(token, position.filename(), position.absolutePosition(), position.line(), position.column());
  }

  public LexInfo(Token token, String filename, int absolutePosition, int line, int column) {
    this.token = token;
    this.line = line;
    this.absolutePosition = absolutePosition;
    this.column = column;
    this.filename = filename;

    // This type needs to be serializable. Can't serialize classes.
    this.typeName = token.getClass().getTypeName();
  }

  public Token getToken() {
    return token;
  }

  public int line() {
    return line;
  }

  public int column() {
    return column;
  }

  public Class type() {
    if (type == null) {
      try {
        this.type = Class.forName(this.typeName);
      } catch (ClassNotFoundException e) {
        throw new NotImplementedException();
      }
    }

    return this.type;
  }

  public int length() {
    return this.representation().length();
  }

  public String representation() {
    return this.token.toString();
  }

  public int absolutePosition() {
    return this.absolutePosition;
  }

  public String filename() {
    return this.filename;
  }

  public <T> boolean is(Class<T> type) {
    return type.isAssignableFrom(this.type());
  }

  public <T> boolean is(String representation) {
    return this.token.toString().equals(representation);
  }

  public <T> boolean is(Class<T> type, String representation) {
    return type.isAssignableFrom(this.type()) && this.token.toString().equals(representation);
  }

  public String toString() {
    return this.representation();
  }

  public String toVerboseString() {
    return String.format("%s (%s %s:%s)", this.token, this.typeName, this.line, this.column);
  }
}
