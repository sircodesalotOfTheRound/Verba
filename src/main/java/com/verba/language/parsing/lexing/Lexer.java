package com.verba.language.parsing.lexing;

import com.verba.language.parsing.info.LexInfo;
import com.verba.language.parsing.info.LexList;
import com.verba.language.parsing.tokenization.Token;

/**
 * Created by sircodesalot on 14-2-20.
 */
public interface Lexer extends Iterable<LexInfo> {
  public boolean isEOF();

  public boolean notEOF();

  public boolean hasNext();

  public LexInfo next();

  public void advance();

  public void advance(int count);

  public int fileLength();

  public String filename();

  public <T extends Token> LexInfo readNext(Class<T> type);

  public <T extends Token> LexInfo readNext(Class<T> type, String representation);

  public LexInfo current();

  public <T extends Token> LexInfo current(Class<T> type);

  public <T extends Token> LexInfo current(Class<T> type, String representation);

  public LexInfo readCurrentAndAdvance();

  public <T extends Token> LexInfo readCurrentAndAdvance(Class<T> type);

  public <T extends Token> LexInfo readCurrentAndAdvance(Class<T> type, String representation);

  public LexInfo peekPrevious();

  public <T extends Token> boolean currentIs(Class<T> type, String representation);

  public <T extends Token> boolean currentIs(Class<T> type);

  public <T extends Token> boolean nextIs(Class<T> type, String representation);

  public <T extends Token> boolean nextIs(Class<T> type);

  public void setUndoPoint();

  public void clearUndoPoint();

  public void rollbackToUndoPoint();

  public LexList readToEndOfLine();

  public LexList peekToEndOfLine();

  public <T extends Token> LexList readToEndOfLineOr(Class<T> type, String representation);

  public <T extends Token> LexList peekToEndOfLineOr(Class<T> type, String representation);

  public LexList read(int count);

  public LexList peek(int count);

  public LexList readUpTo(String... symbols);

  public <T extends Token> LexList readUpTo(Class<T>... symbols);

  public <T extends Token> LexList readUpTo(Class<T> type, String representation);

  public LexList peekUpTo(String... symbols);

  public <T extends Token> LexList peekUpTo(Class<T>... symbols);

  public <T extends Token> LexList peekUpTo(Class<T> type, String representation);

  public String text();

  public void moveToFirst();

  public int size();

  int getCurrentLine();
}
