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

}

