package lesson3.forSubSet;

import kotlin.collections.AbstractMutableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.function.Predicate;

public class SubSet<T extends Comparable<T>> extends AbstractMutableSet<T> implements SortedSet<T> {
    private final SortedSet<T> delegate;
    private final T fromElement;
    private final T toElement;

    public SubSet(SortedSet<T> delegate, T fromElement, T toElement) {
        this.delegate = delegate;
        this.fromElement = fromElement;
        this.toElement = toElement;
    }


    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new SubSetIterator();
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return delegate.comparator();
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return null;
    }

    @Override
    public T first() {
        return null;
    }

    @Override
    public T last() {
        return null;
    }

    public class SubSetIterator implements Iterator<T> {
        private TreeNode visit;
        private Stack<>

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public void remove() {

        }
    }
}

