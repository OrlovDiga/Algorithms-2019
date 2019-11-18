package lesson3;

import kotlin.NotImplementedError;
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
        if (deleteNodeTest(root, (T) o)) {
            size--;
            return true;
        }

        return false;
    }
    /**
     * Time complexity O(h)
     * Space complexity O(1)
     */

    private Node<T> deleteRootNode(Node<T> root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root.right;
        }
        if (root.right == null) {
            return root.left;
        }

        Node<T> next = root.right;
        Node<T> pre = null;

        for(; next.left != null; pre = next, next = next.left);
        next.left = root.left;
        if(root.right != next) {
            pre.left = next.right;
            next.right = root.right;
        }
        return next;
    }

    private boolean deleteNodeTest(Node<T> root, T value) {
        Node<T> currentNode = root;
        Node<T> parentNode = null;

        // find remove's node and his parent node
        while(currentNode != null && currentNode.value != value) {
            parentNode = currentNode;
            if (value.compareTo(currentNode.value) < 0) {
                currentNode = currentNode.left;
            } else if (value.compareTo(currentNode.value) > 0) {
                currentNode = currentNode.right;
            }
        }

        //if the value is not found then return false
        if (currentNode == null) {
            return false;
        }

        //if out remove's node it is root
        if (parentNode == null) {
            this.root = deleteRootNode(currentNode);
            return true;
        }
        if (parentNode.left == currentNode) {
            parentNode.left = deleteRootNode(currentNode);
        } else {
            parentNode.right = deleteRootNode(currentNode);
        }

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
        private Stack<Node<T>> stack;
        private Node<T> visit;

        private BinaryTreeIterator() {
          visit = root;
          stack = new Stack<>();
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return visit != null || !stack.isEmpty();
        }
        /**
         * Time complexity O(1)
         * Space complexity O(h)
         */

        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {
            while (visit != null) {
                stack.push(visit);
                visit = visit.left;
            }

            Node<T> next = stack.pop();
            visit = next.right;
            return next.value;
        }
        /**
         * Time complexity O(1)
         * Space complexity O(h)
         */

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() throws NoSuchElementException {
            throw new NotImplementedError();
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

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return Comparator.naturalOrder();
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SubSet<>(this, fromElement, toElement);
    }
    /**
     * Time complexity O(h)
     * Space complexity O(1)
     */

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
     * Time complexity O(h)
     * Space complexity O(1)
     */

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubSet<>(this, fromElement, null);
    }
    /**
     * Time complexity O(h)
     * Space complexity O(1)
     */

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
