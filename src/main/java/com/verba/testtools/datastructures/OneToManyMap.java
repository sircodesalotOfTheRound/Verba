package com.verba.testtools.datastructures;

import com.javalinq.implementations.QList;
import com.javalinq.implementations.QMap;
import com.javalinq.interfaces.QIterable;

/**
 * Created by sircodesalot on 15/3/18.
 */
public class OneToManyMap<TKey, UMany> {
  private final QMap<TKey, QList<UMany>> map = new QMap<>();

  public void add(TKey key, UMany item) {
    getListForKey(key).add(item);
  }

  public void addMany(TKey key, Iterable<UMany> many) {
    QList<UMany> list = getListForKey(key);
    for (UMany item : many) {
      list.add(item);
    }
  }

  private QList<UMany> getListForKey(TKey key) {
    if (!map.containsKey(key)) {
      map.add(key, new QList<>());
    }

    return map.get(key);
  }

  public QIterable<UMany> getItemsForKey(TKey key) {
    return map.get(key);
  }

  public boolean containsKey(TKey key) {
    return map.containsKey(key);
  }
}
