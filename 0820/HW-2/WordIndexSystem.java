import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordIndexSystem {
  public static void main(String[] args) {
    
    String[] sentences = {
            "Java is a high-level, class-based, object-oriented programming language.",
            "Java is designed to have as few implementation dependencies as possible.",
            "Object-oriented programming is a programming paradigm based on the concept of objects."
        };
    
    Map<String, Integer> wordCountMap = new HashMap<>();
        Set<String> frequentWordsSet = new HashSet<>();

        System.out.println("輸入原始文檔");
        for (String sentence : sentences) {
            System.out.println("- " + sentence);
        }

    for (String sentence : sentences) {
            String cleanedSentence = sentence.replaceAll("[,.]", "").toLowerCase();

            String[] words = cleanedSentence.split("\\s+");

            for (String word : words) {
                if (word.isBlank()) {
                    continue;
                }

                int count = wordCountMap.getOrDefault(word, 0) + 1;
                wordCountMap.put(word, count);

                if (count >= 2) {
                    frequentWordsSet.add(word);
                }
            }
        }
  }
}
