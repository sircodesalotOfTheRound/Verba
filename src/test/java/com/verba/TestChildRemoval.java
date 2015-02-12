package com.verba;

import com.verba.language.graph.visitors.ExpressionTreeVisitor;
import com.verba.language.parse.expressions.VerbaExpression;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/2/12.
 */
public class TestChildRemoval {
  public static class MyVerbaExpression extends VerbaExpression {
    boolean childWasRemoved = false;
    private VerbaExpression removedChild;

    public MyVerbaExpression() {
      super(null, null);
    }

    public MyVerbaExpression(VerbaExpression parent) {
      super(parent, null);
    }

    public boolean childRemoved() { return childWasRemoved; }
    public VerbaExpression removedChild() { return this.removedChild; }

    @Override
    protected void onChildRemoved(VerbaExpression child) {
      this.removedChild = child;
      this.childWasRemoved = true;
    }

    @Override
    public void accept(ExpressionTreeVisitor visitor) {

    }
  }

  @Test
  public void testChildRemoval() {
    MyVerbaExpression parent = new MyVerbaExpression();
    MyVerbaExpression child = new MyVerbaExpression(parent);

    child.setParent(null);
    assert(parent.childRemoved());
    assert(parent.removedChild() == child);
  }
}
