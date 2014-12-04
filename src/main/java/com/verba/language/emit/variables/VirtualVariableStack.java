package com.verba.language.emit.variables;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.variables.frame.VirtualVariableFrame;
import com.verba.language.graph.symbols.table.entries.SymbolTableEntry;
import com.verba.tools.exceptions.CompilerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class VirtualVariableStack {
  private static final Object EMPTY_OBJECT = new Object();

  private final VirtualVariable[] variableArray;
  private final Map<String, VirtualVariable> variablesByName = new HashMap<>();
  private final QSet<Integer> availableRegisters = new QSet<>();
  private final QSet<VirtualVariable> variableSet = new QSet<>();
  private final Stack<VirtualVariableFrame> callFrames = new Stack<>();
  private final Map<VirtualVariable, VirtualVariable.VirtualVariableEventSubscription> eventsPerVariable = new HashMap<>();

  public VirtualVariableStack(int size) {
    this.variableArray = new VirtualVariable[size];

    for (int index = 0; index < size; index++) {
      this.availableRegisters.add(index);
    }

    this.callFrames.push(new VirtualVariableFrame());
  }

  public VirtualVariable addToFrame(String key, SymbolTableEntry type) {
    if (this.containsKey(key)) {
      throw new CompilerException("Key already exists");
    }

    return addToFrame(key, type, nextAvailableIndex());
  }

  public VirtualVariable addToFrame(String key, SymbolTableEntry type, int variableNumber) {
    VirtualVariable variable = new VirtualVariable(key, variableNumber, type);
    addToFrame(variable);

    return variable;
  }

  private void addToFrame(VirtualVariable variable) {
    this.variableArray[variable.variableNumber()] = variable;
    this.variableSet.add(variable);
    this.variablesByName.put(variable.key(), variable);
    this.availableRegisters.remove(variable.variableNumber());
    this.subscribeToVariableEvents(variable);
  }

  private void subscribeToVariableEvents(VirtualVariable variable) {
    VirtualVariable.VirtualVariableEventSubscription eventsForVariable = new VirtualVariable.VirtualVariableEventSubscription() {
      @Override
      public void onRenameVariable(VirtualVariable variable, String newKey) {
        VirtualVariableStack.this.onVariableNameChange(variable, newKey);
      }
    };

    this.eventsPerVariable.put(variable, eventsForVariable);
    variable.addVariableEventSubscription(eventsForVariable);
  }

  private void unsubscripeToVariableEvents(VirtualVariable variable) {
    VirtualVariable.VirtualVariableEventSubscription subscription = this.eventsPerVariable.get(variable);
    variable.addVariableEventSubscription(subscription);

    this.eventsPerVariable.remove(variable);
  }

  public VirtualVariable variableByName(String key) {
    return this.variablesByName.get(key);
  }

  public boolean containsVariableMatching(String key, SymbolTableEntry entry) {
    if (!this.containsKey(key)) {
      return false;
    }

    return this.variableByName(key).type() == entry;
  }

  public VirtualVariable withNewStackFrame(Consumer<Object> callback) {
    this.pushFrame();
    callback.accept(EMPTY_OBJECT);
    return this.popFrame();
  }

  public boolean containsKey(String key) {
    return this.variablesByName.containsKey(key);
  }


  public void pushFrame() {
    this.callFrames.push(new VirtualVariableFrame());
  }

  public void setFrameReturnValue(VirtualVariable variable) {
    this.callFrames.peek().setReturnValue(variable);
  }

  public VirtualVariable popFrame() {
    VirtualVariableFrame frame = this.callFrames.pop();

    // Pop all variables from this frame execpt for the return value.
    QIterable<VirtualVariable> variablesExceptForReturnValue = frame.variables()
      .where(variable -> variable != frame.returnValue());

    for (VirtualVariable variable : variablesExceptForReturnValue) {
      this.clearVariable(variable);
    }

    // Move the return value down to the previous frame.
    if (frame.hasReturnValue() && !this.callFrames.empty()) {
      this.callFrames.peek().add(frame.returnValue());
    }

    return frame.returnValue();
  }

  private void clearVariable(int variableNumber) {
    clearVariable(this.variableArray[variableNumber]);
  }

  public void clearVariable(VirtualVariable variable) {
    this.unsubscripeToVariableEvents(variable);
    this.variableSet.remove(variable);
    this.variablesByName.remove(variable.key());
    this.availableRegisters.add(variable.variableNumber());
  }

  public int size() { return this.variableArray.length; }

  private int nextAvailableIndex() {
    return this.availableRegisters.first();
  }

  public void onVariableNameChange(VirtualVariable variable, String newKey) {
    if (variableSet.contains(variable)) {
      this.variablesByName.remove(variable.key());
      this.variablesByName.put(newKey, variable);
    }
  }
}
