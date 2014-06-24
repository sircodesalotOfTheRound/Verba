package tools.collections;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by sircodesalot on 14-4-30.
 */
public class Grouping<U, T> {
    private final Map<U, QIterable<T>> map;

    public Grouping(QIterable<T> items, Function<T, U> onProperty) {
        this.map = groupBy(items, onProperty);
    }

    private Map<U, QIterable<T>> groupBy(QIterable<T> items, Function<T, U> onProperty) {
        Map<U, QIterable<T>> grouping = new HashMap<>();

        for (T item : items) {
            U projection = onProperty.apply(item);
            if (!grouping.containsKey(projection)) {
                QList<T> list = new QList<>();
                list.add(item);

                grouping.put(projection, list);
                continue;
            }

            QList<T> list = (QList<T>) grouping.get(projection);
            list.add(item);
        }

        return grouping;
    }

    public QIterable<T> get(U key) {
        return map.get(key);
    }

    public boolean containsKey(U key) {
        return map.containsKey(key);
    }
}
