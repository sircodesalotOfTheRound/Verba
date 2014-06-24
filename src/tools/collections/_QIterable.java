package tools.collections;

import com.javalinq.implementations.QList;
import com.javalinq.interfaces.QIterable;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 14-4-26.
 */
public interface _QIterable<T> extends Iterable<T> {
    default <U> QIterable<U> cast(Class<U> toType) {
        QList<U> newSet = new QList<>();
        for (T item : this) newSet.add((U) item);

        return newSet;
    }

    default int indexOf(T object) {
        int index = 0;
        for (T item : this) {
            if (item == object)
                return index;

            index++;
        }

        throw new QueryException("No such item found");
    }

    default boolean any() {
        return this.iterator().hasNext();
    }

    default <U> QIterable<U> ofType(Class<U> type) {
        return this
            .where(item -> type.isAssignableFrom(item.getClass()))
            .map(item -> (U) item);
    }

    default QIterable<T> where(Predicate<T> predicate) {
        QList<T> subset = new QList<>();

        for (T item : this)
            if (predicate.test(item)) {
                subset.add(item);
            }

        return subset;
    }

    default T get(int index) {
        int findIndex = 0;
        for (T item : this) {
            if (index == findIndex) return item;
            findIndex++;
        }

        throw new QueryException("Index out of bounds");
    }

    default <U> U getAs(int index, Class<U> type) {
        return (U) get(index);
    }

    default <U> boolean matchesSequence(U... matchItems) {
        int index = 0;
        for (T item : this) {
            if (!item.equals(matchItems[index++])) return false;
        }

        return true;
    }

    default <U> boolean matchesSequence(Iterable<U> matchItems) {
        return this.matchesSequence(matchItems);
    }

    default T[] toArray(Class<T> type) {
        int size = this.count();
        T[] array = (T[]) Array.newInstance(type, size);
        int index = 0;

        for (T item : this) {
            array[index++] = item;
        }

        return array;
    }

    default boolean hasItems() {
        return this.iterator().hasNext();
    }

    default int count() {
        int count = 0;
        for (T item : this) count++;

        return count;
    }

    default int count(Predicate<T> predicate) {
        int count = 0;
        for (T token : this) if (predicate.test(token)) count++;
        return count;
    }

    default T first() {
        return this.iterator().next();
    }

    default T singleOrNull() {
        Iterator<T> iterator = this.iterator();
        if (!iterator.hasNext()) return null;

        T single = iterator.next();

        if (iterator.hasNext()) throw new QueryException("Found multiple entries");
        else return single;
    }

    default T single() {
        Iterator<T> iterator = this.iterator();
        T single = iterator.next();

        if (iterator.hasNext()) throw new QueryException("Found multiple entries");
        else return single;
    }

    default T first(Predicate<T> predicate) {
        for (T item : this) {
            if (predicate.test(item)) return item;
        }

        throw new QueryException("No matching items found");
    }

    default T firstOrNull(Predicate<T> predicate) {
        for (T item : this) {
            if (predicate.test(item)) return item;
        }

        return null;
    }


    default T single(Predicate<T> predicate) {
        List<T> matches = new ArrayList<>();
        for (T item : this) {
            if (predicate.test(item)) {
                matches.add(item);
            }
        }

        if (matches.size() > 1) throw new QueryException("Found multiple matching entries");
        else if (matches.size() != 1) throw new QueryException("No entries found");
        else return matches.get(0);
    }

    default T singleOrNull(Predicate<T> predicate) {
        List<T> matches = new ArrayList<>();
        for (T item : this) {
            if (predicate.test(item)) {
                matches.add(item);
            }
        }

        if (matches.size() > 1) throw new QueryException("Found multiple matching entries");
        else if (matches.size() == 1) return matches.get(0);
        else return null;
    }

    default <U> QIterable<U> flatten(Function<T, QIterable<U>> flatteningProjection) {
        QList<U> flattenedList = new QList<>();

        for (T item : this) {
            QIterable<U> flattenedItems = flatteningProjection.apply(item);

            for (U flattenedItem : flattenedItems) {
                flattenedList.add(flattenedItem);
            }
        }

        return flattenedList;
    }

    default T last() {
        T last = null;
        for (T item : this) {
            last = item;
        }

        return last;
    }

    default <U> U firstAs(Class<U> type) {
        return (U) this.first();
    }

    default <U> U singleAs(Class<U> type) {
        return (U) this.single();
    }

    default <U> QIterable<U> map(Function<T, U> projection) {
        QList<U> mapped = new QList<>();
        for (T item : this) {
            U mappedValue = projection.apply(item);
            mapped.add(mappedValue);
        }

        return mapped;
    }

    default QIterable<T> distinct() {
        return this.distinctBy(item -> item);
    }

    default <U> QIterable<T> distinctBy(Function<T, U> onProperty) {
        Set<U> seenItems = new HashSet<>();
        QList<T> distinctList = new QList<>();

        for (T item : this) {
            U projection = onProperty.apply(item);

            // If this value hasn't been seen before, then add it
            if (seenItems.add(projection)) distinctList.add(item);
        }

        return distinctList;
    }


    default boolean any(Predicate<T> predicate) {
        for (T item : this)
            if (predicate.test(item)) {
                return true;
            }

        return false;
    }

    default boolean all(Predicate<T> predicate) {
        for (T token : this) if (!predicate.test(token)) return false;
        return true;
    }

}
