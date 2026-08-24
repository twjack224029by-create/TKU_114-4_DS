import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {
   public static void main(String[] args) {
     System.out.println("報名安全清理與重複檢測test \n");

    List<String> rawList = new ArrayList<>(Arrays.asList(
            "張小明",
            "   ",         // 空白
            "李小華",
            null,           // null 
            "張小明",       // 重複姓名
            "",             // 空白
            "陳大文",
            "李小華",       // 重複姓名
            "  王小美  "    // 前後有空白
        ));
     
      System.out.println("【清理前原始名單 (共 " + rawList.size() + " 筆)】");
        printList(rawList);

     Set<String> seenNames = new HashSet<>();
        Set<String> duplicateNames = new HashSet<>();

      Iterator<String> iterator = rawList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
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
          
   }
}
