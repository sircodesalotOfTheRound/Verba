package com.verba.tools.datastructures;

import com.verba.tools.datastructures.iterablemap.IterableMap;
import com.verba.tools.datastructures.iterablemap.KeyValuePair;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/3/16.
 */
public class IterableMapTests {
  @Test
  public void testIterableMap() {
    Map<Integer, String> hashMap = new HashMap<>();
    IterableMap<Integer, String> iterableMap = new IterableMap<>();
    hashMap.put(1, "first");
    hashMap.put(2, "second");
    hashMap.put(3, "third");
    hashMap.put(4, "fourth");
    hashMap.put(5, "fifth");
    hashMap.put(6, "sixth");
    hashMap.put(7, "seventh");
    hashMap.put(8, "eighth");
    hashMap.put(9, "ninth");
    hashMap.put(10, "tenth");
    hashMap.put(11, "eleventh");
    hashMap.put(12, "twelfth");

    for (Integer key : hashMap.keySet()) {
      assert(iterableMap.add(key, hashMap.get(key)));
    }

    for (KeyValuePair<Integer, String> item : iterableMap) {
      assert (item.value().equals(hashMap.get(item.key())));
    }
  }
}
