package lesson3;

import kotlin.NotImplementedError;
import kotlin.collections.AbstractMutableSet;
import lesson3.forSubSet.SubSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T>{

    protected static class Node<T> {
        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        if (deleteNode(root, (T)o)) {
            size--;
            values.add((T) o);
            return true;
        }

        return false;
    }

    private Node<T> findSmallestValue(Node<T> root) {
        return root.left == null ? root : findSmallestValue(root.left);
    }

    private boolean deleteNode(Node<T> root, T value) {
        Node<T> parent = null;
        Node<T> curr = root;

        while (curr != null && curr.value != value) {
            parent = curr;

            if (value.compareTo(curr.value) < 0) {
                curr = curr.left;
            }
            else {
                curr = curr.right;
            }
        }

        if (curr == null) {
            this.root = root;
            return false;
        }
//case 1
        if (curr.right == null && curr.left == null) {
            if (curr != root) {
                if (parent.left == curr) {
                    parent.left = null;
                } else {
                parent.right = null;
                }
            } else {
                root = null;
            }
        }
//case 2
        else if (curr.left != null && curr.right != null) {
            Node<T> successor = findSmallestValue(curr.right);

            T val = successor.value;

            deleteNode(root, successor.value);

            curr.value = val;
        }
//case 3
        else {
            Node<T> child = (curr.left != null)? curr.left: curr.right;

            if (curr != root) {
                if (curr == parent.left) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
            } else {
                root = child;
            }
        }

        this.root = root;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    public class BinaryTreeIterator implements Iterator<T> {
        Stack<Node<T>> stack;

        private BinaryTreeIterator() {
            stack = new Stack<>();

            while (root != null) {
                stack.push(root);
                System.out.println("constructor " + root.value);
                root = root.left;
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {
            Node<T> node = stack.pop();
            T result = node.value;

            if (node.right != null) {
                node = node.right;

                while(node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }

            return result;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() throws NoSuchElementException {
            Node<T> curr = stack.pop();

        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    SortedSet<T> values;

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return (o1, o2) -> o1.compareTo(o2);
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SubSet(this, fromElement, toElement);
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubSet<>(this, null, toElement);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
