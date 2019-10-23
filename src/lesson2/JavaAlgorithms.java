package lesson2;

import kotlin.Pair;
import lesson2.forBalda.Tree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws IOException {
        int[] numArr = Files.readAllLines(Paths.get(inputName), StandardCharsets.UTF_8)
                          .stream()
                          .mapToInt(Integer::parseInt)
                          .toArray();

        int bestMaxSum = 0;
        int currentMaxSum = 0;
        int currentNumStart= 0;
        int numStart = 0;
        int numEnd = 0;

        //Kadane's algorithm O(n)
        for (int i = 0; i < numArr.length - 1; i++) {
            int temp = numArr[i + 1] - numArr[i];

            if (currentMaxSum <= 0) {
                currentNumStart = i;
                currentMaxSum = temp;
            } else {
                currentMaxSum += temp;
            }

            if (currentMaxSum > bestMaxSum) {
                bestMaxSum = currentMaxSum;
                numEnd = i + 2;
                numStart = currentNumStart + 1;
            }
        }

        return new Pair<>(numStart, numEnd);
    }

    /*
     *Time complexity = O(n)
     *Resource complexity = O(n)
     */

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        int result = 0;

        for (int i = 1; i <= menNumber; i++) {
            result = (result + choiceInterval) % i;
        }

        return result + 1;
    }

    /*
     *Time complexity = O(n)
     *Resource complexity = O(1)
     */

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) {
        int [][] matrix = new int[first.length() + 1][ second.length() + 1];
        int maxLenght = -1;
        int index = 0;

        for (int i = 0; i <= first.length(); i++) {
            for (int j = 0; j <= second.length(); j++) {
                if (i == 0 || j == 0 || (first.charAt(i - 1) != second.charAt(j - 1))) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                }

                if (matrix[i][j] > maxLenght) {
                    maxLenght = matrix[i][j];
                    index = i;
                }
            }
        }

        return first.substring(index - maxLenght, index);
    }

    /*
     *Time complexity = O(first.length() * second.length())
     *Resource complexity = O(first.length() * second.length())
     */

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        int i = 3;
        int count = 1;

        if (limit < 2) {
            return 0;
        }

        while (limit >= i) {
            boolean flag = true;

            for (int j = 3; j*j <= i; j += 2) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                count++;
            }

            i += 2;
        }

        return count;
    }

    /*
     *Time complexity = O(n^(1.5))
     *Resource complexity = O(1)
     */

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException {
        char[][] matrix = Files.readAllLines(Paths.get(inputName), StandardCharsets.UTF_8).stream()
                .map(s -> s.replaceAll("[^A-Za-zА-Яа-я]+", "").toCharArray())
                .toArray(char[][]::new);

        Tree trie = new Tree(matrix, words);

        return trie.findWordsInMatrix();
    }

    /*
     *Time complexity = O(lengthMatrix * weightMatrix * wordsLength)
     *Resource complexity = O(lengthMatrix * weightMatrix * wordsLength)
     */
}
