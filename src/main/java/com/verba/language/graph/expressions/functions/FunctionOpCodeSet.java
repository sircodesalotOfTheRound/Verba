package com.verba.language.graph.expressions.functions;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.header.StringTableStringEntry;
import com.verba.language.emit.opcodes.VerbatimOpCodeBase;
import com.verba.language.emit.variables.VirtualVariable;

import java.util.Iterator;

/**
 * Created by sircodesalot on 14/12/3.
 */
public class FunctionOpCodeSet implements QIterable<VerbatimOpCodeBase> {
  private final QList<VerbatimOpCodeBase> opcodes = new QList<>();

  public QIterable<VerbatimOpCodeBase> opcodes() {
    return this.opcodes;
  }

  private void add(VerbatimOpCodeBase opcode) {
    this.opcodes.add(opcode);
  }

  @Override
  public Iterator<VerbatimOpCodeBase> iterator() {
    return this.opcodes.iterator();
  }


  // Factory methods
  public void box(VirtualVariable source, VirtualVariable destination) {
    add(VerbatimOpCodeBase.box(source, destination));
  }

  public void call(StringTableStringEntry functionName) {
    this.add(VerbatimOpCodeBase.call(functionName));
  }

  public void call(StringTableStringEntry functionName, Iterable<VirtualVariable> variables) {
    this.add(VerbatimOpCodeBase.call(functionName, variables));
  }

  public void endFunction() {
    this.add(VerbatimOpCodeBase.endFunction());
  }

  public void ret() {
    this.add(VerbatimOpCodeBase.ret());
  }

  public void loadString(VirtualVariable variable, StringTableStringEntry text) {
    this.add(VerbatimOpCodeBase.loadString(variable, text));
  }

  public void stageArg(VirtualVariable variable) {
    this.add(VerbatimOpCodeBase.stageArg(variable));
  }

  public void loaduint64(VirtualVariable variable, long value) {
    this.add(VerbatimOpCodeBase.loaduint64(variable, value));
  }

}
