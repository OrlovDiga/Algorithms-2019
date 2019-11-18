package lesson3.forSubSet;

import kotlin.collections.AbstractMutableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Iterator;
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
        private Iterator<T> delegate = SubSet.this.delegate.iterator();
        private T nextNode = null;

        public SubSetIterator() {
            //search first need node
            if (fromElement == null) {
                this.nextNode = delegate.next();
            } else {
                while (delegate.hasNext()) {
                    T currentNode = delegate.next();

                    if (currentNode.compareTo(fromElement) >= 0) {
                        this.nextNode = currentNode;
                        break;
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            return nextNode != null && (toElement == null || nextNode.compareTo(toElement) < 0);
        }

        @Override
        public T next() {
            T result = nextNode;
            nextNode = (delegate.hasNext()) ? delegate.next() : null;
            return result;
        }

        @Override
        public void remove() {
            delegate.remove();
        }
    }

    //Да, с размером произошла ситуация не очень, но если вы знаете как можно улучшить, подскажите плес
    @Override
    public int getSize() {
        int size = 0;
        for (T t : this) {
            size++;
        }

        return size;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new SubSetIterator();
    }

    @Override
    public boolean add(T node) {
        if (fromElement == null && toElement.compareTo(node) > 0) {
            return delegate.add(node);
        } else if (toElement == null && fromElement.compareTo(node) < 0) {
            return delegate.add(node);
        } else if (fromElement != null && toElement != null
                   && node.compareTo(fromElement) >= 0 && node.compareTo(toElement) < 0) {
            return delegate.add(node);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean remove(T node) {
        if (fromElement == null && node.compareTo(toElement) < 0) {
            return delegate.remove(node);
        } else if (toElement == null && node.compareTo(fromElement) >= 0) {
            return delegate.remove(node);
        } else if (fromElement != null && toElement != null
                   && node.compareTo(fromElement) >= 0 && node.compareTo(toElement) < 0) {
            return delegate.remove(node);
        } else {
            throw new IllegalArgumentException();
        }
    }

/* У меня есть проблема, я хотел реализовать методы add and remove через отдельный метод,
 но не вышло, так как в аргументе у нас копия, а не сам объект. Вопрос, можно ли это как-то по другому провернуть, сократив ненужный код ?
 P.S. на название тут, пожалуйста, не смотрите, ибо это просто было написано на скорую руку для проверки будет ли вообще работать или нет
    private boolean lambdaMethod(boolean method , T node) {
        if (fromElement == null && toElement == null) {
            throw new IllegalArgumentException();
        } else if (fromElement == null && toElement.compareTo((T) node) > 0) {
            return method;
        } else if (toElement == null && fromElement.compareTo(node) < 0) {
            return method;
        } else if (fromElement != null && toElement != null && node.compareTo(fromElement) >= 0 && node.compareTo(toElement) == -1) {
            return method;
        } else {
            throw new IllegalArgumentException();
        }
    }
*/

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
        return new SubSet<>(this, fromElement, toElement);
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubSet<>(this, null, toElement);
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubSet<>(this, fromElement, toElement);
    }

    @Override
    public T first() {
        return iterator().next();
    }

    @Override
    public T last() {
        Iterator<T> iterator = iterator();
        T tempNode = iterator.next();

        while (iterator.hasNext()) {
            tempNode = iterator.next();
        }

        return tempNode;
    }
}

