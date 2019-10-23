package lesson1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaTasks {

    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        List<String> stringsOfDate = Files.readAllLines(Paths.get(inputName), StandardCharsets.UTF_8);
        List<Date> dates = new ArrayList<>();

        for(String s: stringsOfDate) {
            dates.add(dateFormat.parse(s));
        }

        Collections.sort(dates);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName))) {
            dates.forEach((Date d) -> {
                try {
                    writer.write(dateFormat.format(d));
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /*
     *Time complexity = O(n * log(n))
     *Resource complexity = O(n)
     */

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        List<String> stringList = Files.readAllLines(Paths.get(inputName), StandardCharsets.UTF_8);
        Map<String, List<String>> data = new TreeMap<>(
                Comparator.comparing(s -> s.toString().replaceAll("\\d", ""))
                          .thenComparing(s -> Integer.parseInt(s.toString().replaceAll("\\D", ""))));

        for(int i = 0; i < stringList.size(); i++) {
            String[] temp = stringList.get(i).split(" - ");
            if (!data.containsKey(temp[1])) {
                data.put(temp[1], new ArrayList<>());
            }

            data.get(temp[1]).add(temp[0]);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName))) {
            for (Map.Entry<String, List<String>> entry : data.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue()
                                                               .stream()
                                                               .sorted()
                                                               .collect(Collectors.joining(", ")));
                writer.newLine();
            }
        }
    }

    /*
     *Time complexity = O(n * log(n))
     *Resource complexity = O(n)
     */

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        double[] temperArr = Files.readAllLines(Paths.get(inputName), StandardCharsets.UTF_8)
                                  .stream().mapToDouble(Double::parseDouble).toArray();

        Sorts.heapSort(temperArr);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName))) {
            for (int i = 0; i < temperArr.length; i++) {
                writer.write(temperArr[i] + "\n");
            }
        }
    }

    /*
     *Time complexity = O(n * log(n))
     *Resource complexity = O(n)
     */

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        int[] numbers = Files.readAllLines(Paths.get(inputName), StandardCharsets.UTF_8)
                             .stream().mapToInt(Integer::parseInt).toArray();

        Optional<Map.Entry<Integer, Long>> tempStream = Arrays.stream(numbers)
                .boxed()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())) // it is crutch, sorry :)
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1);

        int maxVal = tempStream.get().getKey();
        long countOfMaxVal = tempStream.get().getValue();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName))) {
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i] != maxVal) {
                    writer.write(numbers[i] + "");
                    writer.newLine();
                }
            }

            for (int i = 0; i < countOfMaxVal; i++) {
                writer.write(maxVal + "");
                writer.newLine();
            }
        }
    }

    /*
     *Time complexity = O(n * log(n))
     *Resource complexity = O(n)
     */

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        System.arraycopy(first, 0, second, 0, first.length);
        Arrays.sort(second);
    }

    /*
     *Time complexity = O(n * log(n))
     *Resource complexity = O(1)
     */
}
