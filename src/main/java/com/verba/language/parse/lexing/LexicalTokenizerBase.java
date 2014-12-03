package com.verba.language.parse.lexing;

import com.verba.language.parse.codestream.CodeStream;
import com.verba.language.parse.info.LexInfo;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14-2-16.
 */
public abstract class LexicalTokenizerBase implements Iterable<LexInfo> {
  protected final CodeStream codeStream;

  public LexicalTokenizerBase(CodeStream codeStream) {
    this.codeStream = codeStream;
  }

  public abstract LexInfo readNext();

  public boolean hasNext() {
    return this.codeStream.hasNext();
  }

  @Override
  public Iterator<LexInfo> iterator() {
    return new Iterator<LexInfo>() {
      @Override
      public boolean hasNext() {
        return LexicalTokenizerBase.this.hasNext();
      }

      @Override
      public LexInfo next() {
        return LexicalTokenizerBase.this.readNext();
      }
    };
  }
}
