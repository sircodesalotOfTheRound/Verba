package com.verba.tools.datastructures;

import com.verba.tools.datastructures.iterablemap.IterableMap;
import com.verba.tools.datastructures.iterablemap.KeyValuePair;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class IterableMapTests {
  @Test
  public void testIterableMap() {
    IterableMap<Integer, String> items = new IterableMap<>();
    items.add(1, "first");
    items.add(2, "second");
    items.add(3, "third");
    items.add(4, "fourth");
    items.add(5, "fifth");
    items.add(6, "sixth");
    items.add(7, "seventh");
    items.add(8, "eigth");
    items.add(9, "ninth");
    items.add(10, "tenth");
    items.add(11, "eleventh");
    items.add(12, "twelfth");

    for (KeyValuePair<Integer, String> item : items) {
      System.out.println(item.value());
    }
  }
}
