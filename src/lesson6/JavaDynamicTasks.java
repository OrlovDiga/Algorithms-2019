package lesson6;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int lengthFirst = first.length();
        int lengthSec = second.length();
        int[][] L = new int[lengthFirst+1][lengthSec+1];

        for (int i = 0; i <= lengthFirst; i++) {
            for (int j=0; j <= lengthSec; j++) {
                if (i == 0 || j == 0) {
                    L[i][j] = 0;
                } else if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    L[i][j] = L[i - 1][j - 1] + 1;
                } else {
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
            }
        }

        int index = L[lengthFirst][lengthSec];
        int temp = index;

        char[] lcs = new char[index+1];
        lcs[index] = ' ';

        int i = lengthFirst, j = lengthSec;
        while (i > 0 && j > 0) {
            if (first.charAt(i-1) == second.charAt(j-1)) {
                lcs[index-1] = first.charAt(i-1);

                i--;
                j--;
                index--;
            }
            else if (L[i-1][j] > L[i][j-1]) {
                i--;
            } else {
                j--;
            }
        }

        StringBuilder res = new StringBuilder();
        for(int k=0;k < temp;k++) {
            res.append(lcs[k]);
        }

        return res.toString();
    }
    /**
    * Time complexity: O(first.length * second.length)
    * Memory complexity: O(first.length * second.length)
    */

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        int[][] data = new int[list.size()][2];
        int maxLength = 0;

        for(int i=0;i<list.size();++i){
            data[i][0] = -1;
            data[i][1] = 1;

            for(int j=i-1;j>=0;--j){
                if(list.get(i) > list.get(j)){
                    if(data[i][1] <= data[j][1] + 1) {
                        data[i][1] = data[j][1] + 1;
                        data[i][0] = j;
                    }
                }
            }
            maxLength = Math.max(maxLength,data[i][1]);
        }

        List<Integer> result = new ArrayList<>();

        for(int i=0;i<list.size();++i){
            if(data[i][1] == maxLength){
                int current = i;

                while(current != -1){
                    result.add(list.get(current));
                    current = data[current][0];
                }
                break;
            }
        }
        Collections.reverse(result);

        return result;
    }
    /**
     * Time complexity: O(n^2)
     * Memory complexity: O(n)
     */

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
