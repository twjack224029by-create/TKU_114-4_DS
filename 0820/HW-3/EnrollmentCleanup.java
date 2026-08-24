import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {

    public static void main(String[] args) {
        System.out.println("報名清理與重複檢測test\n");

        List<String> rawList = new ArrayList<>(Arrays.asList(
            "張小明",
            "   ",         // 空白
            "李小華",
            null,           
            "張小明",       // 重複姓名
            "",             
            "陳大文",
            "李小華",       // 重複姓名
            "  王小美  "    // 前後有空白
        ));

        System.out.println("清理前的名單 (共 " + rawList.size() + " 筆)");
        printList(rawList);

        Set<String> seenNames = new HashSet<>();
        Set<String> duplicateNames = new HashSet<>();

        Iterator<String> iterator = rawList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();

            if (name == null || name.trim().isEmpty()) {
                iterator.remove(); 
            } else {
                String cleanName = name.trim();
                
                if (!seenNames.add(cleanName)) {
                    duplicateNames.add(cleanName);
                }
            }
        }

        for (int i = 0; i < rawList.size(); i++) {
            rawList.set(i, rawList.get(i).trim());
        }

        System.out.println("\n清理後的名單 (共 " + rawList.size() + " 筆，包含重複者)");
        printList(rawList);

        System.out.println("重複姓名");
        System.out.println("重複出現的姓名數量: " + duplicateNames.size());
        System.out.println("重複的姓名清單: " + duplicateNames);
    }

    private static void printList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            if (item == null) {
                System.out.println(" [" + i + "] null");
            } else if (item.trim().isEmpty()) {
                System.out.println(" [" + i + "] \"\" (空白字串)");
            } else {
                System.out.println(" [" + i + "] " + item);
            }
        }
    }
}
