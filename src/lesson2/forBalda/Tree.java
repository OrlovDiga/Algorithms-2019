package lesson2.forBalda;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tree {
    private char[][] matrix;
    private Set<String> words;
    private NodeOfTree root;

    public Tree(char[][] matrix, Set<String> words) {
        this.matrix = matrix;
        this.words = words;
        root = new NodeOfTree();
        for (String word: words) {
            insert(word);
        }
    }

    public void insert(String word) {
        Map<Character, NodeOfTree> childs =root.children;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            NodeOfTree t;

            if (childs.containsKey(c)) {
                t = childs.get(c);
            } else {
                t = new NodeOfTree(c);
                childs.put(c, t);
            }

            childs = t.children;

            if (i == word.length() - 1) {
                t.isLeaf = true;
            }
        }
    }

    private boolean isSafe(int i, int j, boolean[][] visited) {
        return (i >= 0 && i < visited.length && j >= 0 && j < visited[i].length && !visited[i][j]);
    }

    private void searchWord(String str, char[][] matrix, int i, int j, boolean[][] visited, NodeOfTree root, Set<String> result) {
        if (root.isLeaf) {
            result.add(str);
        }

        if (isSafe(i, j, visited)) {
            visited[i][j] = true;

            for (Character c: root.children.keySet()) {
                NodeOfTree temp = root.children.get(c);

                if (isSafe(i, j + 1, visited) && matrix[i][j + 1] == c) {
                    searchWord(str + c, matrix, i, j + 1, visited, temp, result);
                }

                if (isSafe(i + 1, j, visited) && matrix[i + 1][j] == c) {
                    searchWord(str + c, matrix, i + 1, j, visited, temp, result);
                }

                if (isSafe(i, j - 1, visited) && matrix[i][j - 1] == c) {
                    searchWord(str + c, matrix, i, j - 1, visited, temp, result);
                }

                if (isSafe(i - 1, j, visited) && matrix[i - 1][j] == c) {
                    searchWord(str + c, matrix, i - 1, j, visited, temp, result);
                }
            }

            visited[i][j] = false;
        }
    }

    public Set<String> findWordsInMatrix() {
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        String str = "";
        Set<String> result = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (root.children.containsKey(matrix[i][j])) {
                    str += matrix[i][j];

                    NodeOfTree tempRoot = root.children.get(matrix[i][j]);

                    searchWord(str, matrix, i, j, visited, tempRoot, result);

                    str = "";
                }
            }
        }

        return result;
    }


}
