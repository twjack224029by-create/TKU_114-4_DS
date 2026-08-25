import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorHistory {
    private final Deque<String> undoStack = new ArrayDeque<>();
    private final Deque<String> redoStack = new ArrayDeque<>();
    private String currentText = "";

  public void type(String newText) {
        if (newText == null) return;
        undoStack.push(currentText);
        currentText = currentText.isEmpty() ? newText : currentText + newText;
        redoStack.clear();
        System.out.println("輸入: \"" + newText + "\"");
        printStatus();
  }

   public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("失敗,Undo歷史紀錄為空");
            return;
        }
     
        redoStack.push(currentText);
        currentText = undoStack.pop();

        System.out.println("執行Undo");
        printStatus();
   }

  public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("失敗,Redo歷史紀錄為空");
            return;
        }
        undoStack.push(currentText);
        currentText = redoStack.pop();
         System.out.println("-> 執行 Redo");
        printStatus();
  }

  public void printStatus() {
        System.out.println("   當前文字: \"" + currentText + "\"");
        System.out.println("   Undo Stack: " + undoStack);
        System.out.println("   Redo Stack: " + redoStack);
    }
}
