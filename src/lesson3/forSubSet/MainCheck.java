package lesson3.forSubSet;

import lesson3.BinaryTree;

import java.util.Iterator;
import java.util.SortedSet;

public class MainCheck {
    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>();

        tree.add(5);
        tree.add(31);
        tree.add(33);
        tree.add(1);
        tree.add(2);
        tree.add(4);
        tree.add(5);
        tree.add(7);
        tree.add(22);

        SortedSet set = tree.subSet(null, null);

        tree.add(0);



        Iterator iter =  set.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }



    }
}
