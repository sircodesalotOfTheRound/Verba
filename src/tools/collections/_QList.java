package tools.collections;

import com.javalinq.interfaces.QIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 14-2-25.
 */
public class _QList<T> implements QIterable<T> {
    List<T> items = new ArrayList<T>();

    public _QList(Iterable<T> iterable) {
        for (T item : iterable) {
            this.items.add(item);
        }
    }

    public _QList() {
    }

    public _QList(T[] data) {
        for (T item : data) {
            this.items.add(item);
        }
    }

    public void add(Iterable<T> items) {
        for (T item : items) this.add(item);
    }

    public void add(T item) {
        this.items.add(item);
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }


    @Override
    public long count() {
        return items.size();
    }

    public void clear() {
        this.items.clear();
    }
}
