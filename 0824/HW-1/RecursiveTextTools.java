public class RecursiveTextTools {

    public static String reverse(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return str.substring(str.length() - 1) + reverse(str.substring(0, str.length() - 1));
    }

    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return isPalindromeHelper(cleaned, 0, cleaned.length() - 1);
    }

    private static boolean isPalindromeHelper(String str, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        return isPalindromeHelper(str, left + 1, right - 1);
    }

    public static int countCharacter(String str, char target) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        int count = (str.charAt(0) == target) ? 1 : 0;

        return count + countCharacter(str.substring(1), target);
    }

    public static void main(String[] args) {
        System.out.println("RecursiveTextTools test ");

        System.out.println("reverse test");
        String[] reverseTests = {"", "A", "Level", "Hello World!", "Data Structures"};
        for (String test : reverseTests) {
            System.out.printf("原始: \"%-15s\" -> 反轉: \"%s\"%n", test, reverse(test));
        }

        System.out.println("\n isPalindrome忽略大小寫與空白");
        String[] palindromeTests = {
            "",                             
            "A",                            
            "Level",                        
            "A man a plan a canal Panama",  
            "Race car",                    
            "Hello",                       
            "Java"                          
        };

        for (String test : palindromeTests) {
            System.out.printf("字串: \"%-30s\" -> 是否為迴文: %b%n", test, isPalindrome(test));
        }

        System.out.println("\n countCharacter test");
        System.out.println("字串: \"Data Structures\", 目標 'a' -> 次數: " + countCharacter("Data Structures", 'a'));
        System.out.println("字串: \"Level\", 目標 'l'           -> 次數: " + countCharacter("Level", 'l'));
        System.out.println("字串: \"Level\", 目標 'L'           -> 次數: " + countCharacter("Level", 'L'));
        System.out.println("字串: \"\", 目標 'x'                -> 次數: " + countCharacter("", 'x'));
        System.out.println("字串: \"AAAAA\", 目標 'A'             -> 次數: " + countCharacter("AAAAA", 'A'));
    }
}
