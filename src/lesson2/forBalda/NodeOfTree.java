package lesson2.forBalda;

import java.util.HashMap;
import java.util.Map;

public class NodeOfTree {
    char c;
    Map<Character, NodeOfTree> children = new HashMap<>();
    boolean isLeaf = false;

    public NodeOfTree() { }

    public NodeOfTree(char c) {
        this.c = c;
    }
}