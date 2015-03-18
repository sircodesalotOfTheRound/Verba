package com.verba.supplementary;

import com.verba.tools.datastructures.OneToManyMap;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class TestOneToManyMap {
  @Test
  public void testOneManyMap() {
    OneToManyMap<Integer, Integer> oddsAndEvens = new OneToManyMap<>();
    for (int index = 0; index < 100; index++) {
      oddsAndEvens.add(index % 2, index);
    }

    assert (oddsAndEvens.containsKey(0));
    assert (oddsAndEvens.containsKey(1));
    assert (!oddsAndEvens.containsKey(2));

    for (int even : oddsAndEvens.getItemsForKey(0)) {
      assert (even % 2 == 0);
    }

    for (int odd : oddsAndEvens.getItemsForKey(1)) {
      assert (odd % 2 == 1);
    }
  }
}
