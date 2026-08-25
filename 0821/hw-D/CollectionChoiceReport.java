import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CollectionChoiceReport {
  public static void main(String[] args) {
    System.out.println("集合選擇報告與操作實作");

    System.out.println("保留搜尋紀錄且允許重複");
    System.out.println("選擇 Interface: List");
    System.out.println("選擇 Implementation: ArrayList");
    System.out.println("原因: 需要維護元素加入的先後順序，且允許相同的搜尋關鍵字重複出現。");

    List<String> searchHistory = new ArrayList<>();
    searchHistory.add("Java 泛型");
    searchHistory.add("Data Structures");
    searchHistory.add("Java 泛型"); 
    searchHistory.add("Collections");

    System.out.println("操作結果: " + searchHistory);

    System.out.println("保存不重複會員編號");
    System.out.println("選擇 Interface: Set");
    System.out.println("選擇 Implementation: HashSet");
    System.out.println("原因: 確保集合內的會員編號唯一，並提供 O(1) 的快速新增與重複檢查。");

    Set<String> memberIds = new HashSet<>();
    boolean add1 = memberIds.add("M001");
    boolean add2 = memberIds.add("M002");
    boolean add3 = memberIds.add("M001"); 

    System.out.println("加入 M001: " + add1);
    System.out.println("加入 M002: " + add2);
    System.out.println("重複加入 M001: " + add3 + " (已自動阻擋)");
    System.out.println("操作結果: " + memberIds);

    System.out.println("以學號查詢成績");
    System.out.println("選擇 Interface: Map");
    System.out.println("選擇 Implementation: HashMap");
    System.out.println("原因: 建立學號 (Key) 對應成績 (Value) 的鍵值對，支援 O(1) 快速查詢。");

    Map<String, Integer> studentScores = new HashMap<>();
    studentScores.put("S101", 88);
    studentScores.put("S102", 95);
    studentScores.put("S103", 72);

    System.out.println("查詢S102成績: " + studentScores.get("S102") + " 分");
    System.out.println("查詢不存在學號 S999: " + studentScores.get("S999"));
    System.out.println("操作結果: " + studentScores);

    System.out.println("依序處理列印工作");
    System.out.println("選擇 Interface: Queue");
    System.out.println("選擇 Implementation: ArrayDeque");
    System.out.println("原因: 需要先進先出 (FIFO) 的結構，後來的列印工作必須排在後方處理。");

    Queue<String> printJobs = new ArrayDeque<>();
    printJobs.offer("Doc1_Homework.pdf");
    printJobs.offer("Doc2_Report.docx");
    printJobs.offer("Doc3_Image.png");

    System.out.println("加入 3 個列印工作");
    System.out.println("處理下一個工作 (poll): " + printJobs.poll());
    System.out.println("剩餘等待列印工作: " + printJobs);

    System.out.println("復原最近操作");
    System.out.println("選擇 Interface: Deque (用作 Stack)");
    System.out.println("選擇 Implementation: ArrayDeque");
    System.out.println("原因: 需要後進先出 (LIFO) 的堆疊行為，優先取回並復原最後發生的動作。");

    Deque<String> actionHistory = new ArrayDeque<>();
    actionHistory.push("輸入文字 'Hello'");
    actionHistory.push("修改字體大小");
    actionHistory.push("刪除段落");

    System.out.println("堆疊加入 3 個操作紀錄");
    System.out.println("執行復原 Undo (pop): " + actionHistory.pop());
    System.out.println("剩餘操作紀錄 Stack: " + actionHistory);
  }
}
