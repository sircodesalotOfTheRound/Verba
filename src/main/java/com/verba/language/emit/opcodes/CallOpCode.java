package com.verba.language.emit.opcodes;

import com.javalinq.implementations.QList;
import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.images.interfaces.ObjectImageOutputStream;
import com.verba.language.emit.variables.VirtualVariable;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class CallOpCode extends VerbatimOpCodeBase {
  private static final int callWithDiscard = 0x43;
  private static final int callWithRetain = 0x44;
  private static final String opName = "Call";

  private final StringTableStringEntry function;
  private final Iterable<VirtualVariable> variables;
  private final VirtualVariable storeLocation;

  public CallOpCode(StringTableStringEntry function, Iterable<VirtualVariable> variables) {
    super(callWithDiscard, opName);

    this.function = function;
    this.variables = variables;
    this.storeLocation = null;
  }

  public CallOpCode(StringTableStringEntry function, VirtualVariable storeLocation, Iterable<VirtualVariable> variables) {
    super(callWithRetain, opName);

    this.function = function;
    this.variables = variables;
    this.storeLocation = storeLocation;
  }

  public CallOpCode(StringTableStringEntry function) {
    this(function, new QList<>());
  }

  public CallOpCode(StringTableStringEntry function, VirtualVariable storeLocation) {
    this (function, storeLocation, new QList<>());
  }

  @Override
  public void render(ObjectImageOutputStream renderer) {
    renderer.writeString("function-name", function);
  }

  public StringTableStringEntry function() { return this.function; }
}
