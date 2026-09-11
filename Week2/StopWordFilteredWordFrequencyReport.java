import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class StopWordFilteredWordFrequencyReport {

    static void generateReport(String text) {

        Set<String> stopWords = new HashSet<>();

        stopWords.add("the");
        stopWords.add("is");
        stopWords.add("a");
        stopWords.add("an");
        stopWords.add("and");
        stopWords.add("to");
        stopWords.add("of");
        stopWords.add("in");
        stopWords.add("on");
        stopWords.add("for");

        String[] words = text.toLowerCase().split("\\s+");

        Map<String, Integer> frequency = new HashMap<>();

        for (String word : words) {

            word = word.replaceAll("[^a-z]", "");

            if (word.isEmpty() || stopWords.contains(word)) {
                continue;
            }

            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        generateReport(text);

        sc.close();
    }
}