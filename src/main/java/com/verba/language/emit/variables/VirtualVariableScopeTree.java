package com.verba.language.emit.variables;

import com.javalinq.implementations.QSet;
import com.javalinq.interfaces.QIterable;
import com.verba.language.emit.variables.frame.VirtualVariableScope;
import com.verba.language.graph.symbols.table.entries.Symbol;
import com.verba.testtools.exceptions.CompilerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Created by sircodesalot on 14/9/20.
 */
public class VirtualVariableScopeTree {
  private static final Object EMPTY_OBJECT = new Object();

  private final VirtualVariable[] variableArray;
  private final Map<String, VirtualVariable> variablesByName = new HashMap<>();
  private final QSet<Integer> availableRegisters = new QSet<>();
  private final QSet<VirtualVariable> variableSet = new QSet<>();
  private final Stack<VirtualVariableScope> scopes = new Stack<>();
  private final Map<VirtualVariable, VirtualVariable.VirtualVariableEventSubscription> eventsPerVariable = new HashMap<>();

  public VirtualVariableScopeTree(int size) {
    this.variableArray = new VirtualVariable[size];

    for (int index = 0; index < size; index++) {
      this.availableRegisters.add(index);
    }

    this.scopes.push(new VirtualVariableScope());
  }

  public VirtualVariable addtoScope(String key, Symbol type) {
    if (this.containsKey(key)) {
      throw new CompilerException("Key already exists");
    }

    return addToScope(key, type, nextAvailableIndex());
  }

  public VirtualVariable addToScope(String key, Symbol type, int variableNumber) {
    VirtualVariable variable = new VirtualVariable(key, variableNumber, type);
    addToScope(variable);

    return variable;
  }

  private void addToScope(VirtualVariable variable) {
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
        VirtualVariableScopeTree.this.onVariableNameChange(variable, newKey);
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

  public boolean containsVariableMatching(String key, Symbol entry) {
    if (!this.containsKey(key)) {
      return false;
    }

    return this.variableByName(key).type() == entry;
  }

  public VirtualVariable withNewVariableScope(Consumer<Object> callback) {
    this.pushScope();
    callback.accept(EMPTY_OBJECT);
    return this.popScope();
  }

  public boolean containsKey(String key) {
    return this.variablesByName.containsKey(key);
  }


  public void pushScope() {
    this.scopes.push(new VirtualVariableScope());
  }

  public void setScopeValue(VirtualVariable variable) {
    this.scopes.peek().setScopeValue(variable);
  }

  public VirtualVariable popScope() {
    VirtualVariableScope frame = this.scopes.pop();

    // Pop all variables from this frame execpt for the return value.
    QIterable<VirtualVariable> variablesExceptForReturnValue = frame.variables()
      .where(variable -> variable != frame.returnValue());

    for (VirtualVariable variable : variablesExceptForReturnValue) {
      this.clearVariable(variable);
    }

    // Move the return value down to the previous frame.
    if (frame.hasReturnValue() && !this.scopes.empty()) {
      this.scopes.peek().add(frame.returnValue());
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
