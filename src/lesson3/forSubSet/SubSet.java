package lesson3.forSubSet;

import kotlin.collections.AbstractMutableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public class SubSet<T extends Comparable<T>> extends AbstractMutableSet<T> implements SortedSet<T> {
    private final SortedSet<T> delegate;
    private final T fromElement;
    private final T toElement;


    public SubSet(SortedSet<T> delegate, T fromElement, T toElement) {
        this.delegate = delegate;
        this.fromElement = fromElement;
        this.toElement = toElement;
    }

    public class SubSetIterator implements Iterator<T> {
        private Iterator delegate = SubSet.this.delegate.iterator();
        private T next = null;

        public SubSetIterator() {
            while (delegate.hasNext()) {
                if (fromElement == null) {
                    this.next = (T) delegate.next();
                    break;
                }

                T next = (T) delegate.next();

                if (next.compareTo(fromElement) >= 0) {
                    this.next = next;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            if (next == null) {
                return false;
            }

            T n = next;

            if (toElement != null && n.compareTo(toElement) >= 0) {
                return false;
            }

            return true;
        }

        @Override
        public T next() {
            if (next == null) {
                throw new NoSuchElementException();
            }

            T result = next;

            if (delegate.hasNext()) {
                next = (T) delegate.next();
            } else {
                next = null;
            }

            return result;
        }

        @Override
        public void remove() {
            delegate.remove();
        }
    }


    @Override
    public int getSize() {
        int size = 0;
        Iterator iter = iterator();
        while (iter.hasNext()) {
            iter.next();
            size++;
        }

        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new SubSetIterator();
    }

    @Override
    public boolean add(T t) {
        if (fromElement == null && toElement == null) {
            throw new IllegalArgumentException();
        } else if (fromElement == null && toElement.compareTo((T) t) > 0) {
            return delegate.add(t);
        } else if (toElement == null && fromElement.compareTo(t) < 0) {
            return delegate.add(t);
        } else if (fromElement != null && toElement != null && t.compareTo(fromElement) >= 0 && t.compareTo(toElement) == -1) {
            return delegate.add(t);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean remove(T t) {
        if (fromElement == null && toElement == null) {
            return false;
        }
        if (fromElement == null && t.compareTo(toElement) == -1) {
            return delegate.remove(t);
        }
        if (toElement == null && t.compareTo(fromElement) >= 0) {
            return delegate.remove(t);
        }
        if (t.compareTo(fromElement) >= 0 && t.compareTo(toElement) == -1) {
            return delegate.remove(t);
        } else {
            return false;
        }
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
        return new SubSet<T>(this, fromElement, toElement);
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubSet<T>(this, null, toElement);
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubSet<T>(this, fromElement, toElement);
    }

    @Override
    public T first() {
        return iterator().next();
    }

    @Override
    public T last() {
        Iterator<T> iterator = iterator();
        T element = iterator.next();

        while (iterator.hasNext()) {
            element = iterator.next();
        }

        return element;
    }
}

