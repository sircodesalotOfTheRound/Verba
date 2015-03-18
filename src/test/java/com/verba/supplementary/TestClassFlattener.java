package com.verba.supplementary;

import com.verba.testtools.polymorphism.ClassHierarchyFlattener;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class TestClassFlattener {
  @Test
  public void testAgainstThisClass() {
    ClassHierarchyFlattener flattener = new ClassHierarchyFlattener(TestClassFlattener.class);
    assert (flattener.type() == TestClassFlattener.class);
    assert (flattener.flattenedHierarchy().count() == 2);
    assert (flattener.flattenedHierarchy().single(type -> type == TestClassFlattener.class) != null);
    assert (flattener.flattenedHierarchy().single(type -> type == Object.class) != null);
  }

  public interface MyInterface { }
  public static class BaseClass { }
  public static class TesterClass extends BaseClass implements MyInterface { }


  @Test
  public void testAgainstTesterClass() {
    ClassHierarchyFlattener flattener = new ClassHierarchyFlattener(TesterClass.class);
    assert (flattener.type() == TesterClass.class);
    assert (flattener.flattenedHierarchy().count() == 4);
    assert (flattener.flattenedHierarchy().single(type -> type == TesterClass.class) != null);
    assert (flattener.flattenedHierarchy().single(type -> type == BaseClass.class) != null);
    assert (flattener.flattenedHierarchy().single(type -> type == MyInterface.class) != null);
    assert (flattener.flattenedHierarchy().single(type -> type == Object.class) != null);
  }
}
