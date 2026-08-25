class Task {
    private final String id;
    private final String description;

    public Task(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + description;
    }
}

class TaskNode {
    Task task;
    TaskNode next;

    public TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private int size;

    public TaskLinkedList() {
        this.head = null;
        this.size = 0;
    }

      public int size() {
        return size;
    }

  public boolean containsId(String id) {
        if (id == null) return false;
        TaskNode current = head;
        while (current != null) {
            if (current.task.getId().equalsIgnoreCase(id)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean addFirst(Task task) {
        if (task == null || containsId(task.getId())) {
            System.out.println("失敗,Task 為空或 ID [" + (task != null ? task.getId() : "null") + "] 已存在");
            return false;
        }

        TaskNode newNode = new TaskNode(task);
        newNode.next = head;
        head = newNode;
        size++;
        System.out.println("-> [addFirst] 成功加入: " + task);
        return true;
    }

    public boolean addLast(Task task) {
        if (task == null || containsId(task.getId())) {
            System.out.println("失敗,Task 為空或 ID [" + (task != null ? task.getId() : "null") + "] 已存在");
            return false;
        }

        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        System.out.println("-> [addLast] 成功加入: " + task);
        return true;
    }

    public Task findById(String id) {
        if (id == null) return null;
        TaskNode current = head;
        while (current != null) {
            if (current.task.getId().equalsIgnoreCase(id)) {
                return current.task;
            }
            current = current.next;
        }
        return null;
    }

    public boolean insertAfter(String existingId, Task task) {
        if (task == null || containsId(task.getId())) {
            System.out.println("失敗,新 Task 為空或 ID [" + (task != null ? task.getId() : "null") + "] 已存在");
            return false;
        }

        TaskNode current = head;
        while (current != null) {
            if (current.task.getId().equalsIgnoreCase(existingId)) {
                TaskNode newNode = new TaskNode(task);
                newNode.next = current.next;
                current.next = newNode;
                size++;
                System.out.println("[insertAfter] 成功在 [" + existingId + "] 後插入: " + task);
                return true;
            }
            current = current.next;
        }

        System.out.println("失敗,找不到目標 ID [" + existingId + "]");
        return false;
    }

    public boolean removeById(String id) {
        if (head == null || id == null) {
            System.out.println("刪除失敗,清單為空或 ID 無效");
            return false;
        }

        if (head.task.getId().equalsIgnoreCase(id)) {
            System.out.println("[removeById] 成功刪除頭節點 (Head): " + head.task);
            head = head.next;
            size--;
            return true;
        }

        TaskNode current = head;
        while (current.next != null) {
            if (current.next.task.getId().equalsIgnoreCase(id)) {
                Task target = current.next.task;
                current.next = current.next.next; 
                size--;
                System.out.println("[removeById] 成功刪除節點: " + target);
                return true;
            }
            current = current.next;
        }

        System.out.println("失敗,找不到 ID [" + id + "]");
        return false;
    }

    public void printAll() {
        System.out.print("Current List (size=" + size + "): ");
        if (head == null) {
            System.out.println("[ Empty List ]");
            return;
        }

        StringBuilder sb = new StringBuilder();
        TaskNode current = head;
        while (current != null) {
            sb.append(current.task.toString());
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        System.out.println(sb.toString());
    }
}

public class LinkedTaskListSystem {

    public static void main(String[] args) {
        System.out.println("LinkedTaskListSystem test \n");
        TaskLinkedList list = new TaskLinkedList();

        System.out.println("空 List 狀態與搜尋/刪除");
        list.printAll();
        System.out.println("嘗試在空清單中刪除 T001: " + list.removeById("T001"));
        System.out.println("嘗試在空清單中尋找 T001: " + list.findById("T001"));

        System.out.println("\n addFirst & addLast");
        list.addLast(new Task("T002", "撰寫報告"));
        list.addFirst(new Task("T001", "開會確認需求"));  
        list.addLast(new Task("T004", "佈署系統"));     
        list.printAll();

        System.out.println("\n 指定位置插入與重複ID");
        list.insertAfter("T002", new Task("T003", "進行單元測試")); 
        list.addLast(new Task("T001", "重複的 T001")); 
        list.printAll();

        System.out.println("\n 搜尋節點findById");
        System.out.println("搜尋存在 ID [T003]: " + list.findById("T003"));
        System.out.println("搜尋不存在 ID [T999]: " + list.findById("T999"));

        System.out.println("\n 刪除中間節點removeById T002");
        list.removeById("T002");
        list.printAll();

        System.out.println("\n 刪除頭節點removeById T001Head");
        list.removeById("T001");
        list.printAll();

        System.out.println("\n 刪除尾節點removeById T004Tail");
        list.removeById("T004");
        list.printAll();

        System.out.println("\n 清空清單");
        list.removeById("T003"); 
        list.printAll();
    }
}

