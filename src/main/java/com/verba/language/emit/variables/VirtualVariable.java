package com.verba.language.emit.variables;

import com.javalinq.implementations.QSet;
import com.verba.language.graph.symbols.table.entries.Symbol;

/**
 * Created by sircodesalot on 14/9/19.
 */
public class VirtualVariable {
  private final int variableNumber;
  private final Symbol type;
  private QSet<VirtualVariableEventSubscription> eventSubscriptions = new QSet<>();
  private String key;

  public interface VirtualVariableEventSubscription {
    void onRenameVariable(VirtualVariable variable, String newKey);
  }

  public VirtualVariable(String key, int variableNumber, Symbol type) {
    this.key = key;
    this.variableNumber = variableNumber;
    this.type = type;
  }

  public int variableNumber() { return this.variableNumber; }
  public Symbol type() { return this.type; }
  public String key() { return key; }

  public void renameVariable(String newKey) {
    for (VirtualVariableEventSubscription subscription : eventSubscriptions) {
      subscription.onRenameVariable(this, newKey);
    }

    this.key = newKey ;
  }

  public void addVariableEventSubscription(VirtualVariableEventSubscription subscription) {
    this.eventSubscriptions.add(subscription);
  }

  public void removeVariableEventSubscriptions(VirtualVariableEventSubscription subscription) {
    this.eventSubscriptions.remove(subscription);
  }
}
