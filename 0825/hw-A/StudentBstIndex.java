import java.util.ArrayList;
import java.util.List;

class Student {
    private final String studentId;
    private String name;
    private String department;

    public Student(String studentId, String name, String department) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "[" + studentId + "] " + name + " (" + department + ")";
    }
}

class BSTNode {
    Student data;
    BSTNode left;
    BSTNode right;

    public BSTNode(Student student) {
        this.data = student;
        this.left = null;
        this.right = null;
    }
}

public class StudentBstIndex {

    private BSTNode root;

    public StudentBstIndex() {
        this.root = null;
    }

    public Student search(String studentId) {
        if (studentId == null || studentId.isBlank()) return null;
        return searchHelper(root, studentId);
    }

    private Student searchHelper(BSTNode node, String studentId) {
        if (node == null) {
            return null;
        }

        int cmp = studentId.compareTo(node.data.getStudentId());
        if (cmp == 0) {
            return node.data; 
        } else if (cmp < 0) {
            return searchHelper(node.left, studentId);
        } else {
            return searchHelper(node.right, studentId);
        }
    }

    public boolean insert(Student student) {
        if (student == null || student.getStudentId() == null) {
            System.out.println("失敗,無效的資料");
            return false;
        }

        if (search(student.getStudentId()) != null) {
            System.out.println("失敗,學號 [" + student.getStudentId() + "] 已存在，不可重複加入");
            return false;
        }

        root = insertHelper(root, student);
        System.out.println("成功加入學生: " + student);
        return true;
    }

    private BSTNode insertHelper(BSTNode node, Student student) {
        if (node == null) {
            return new BSTNode(student);
        }

        int cmp = student.getStudentId().compareTo(node.data.getStudentId());
        if (cmp < 0) {
            node.left = insertHelper(node.left, student);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, student);
        }
        return node;
    }

    public boolean delete(String studentId) {
        if (search(studentId) == null) {
            System.out.println("失敗ㄝ找不到學號 [" + studentId + "] 的資料");
            return false;
        }

        root = deleteHelper(root, studentId);
        System.out.println("成功刪除學號 [" + studentId + "] 的學生資料");
        return true;
    }

    private BSTNode deleteHelper(BSTNode node, String studentId) {
        if (node == null) return null;

        int cmp = studentId.compareTo(node.data.getStudentId());
        if (cmp < 0) {
            node.left = deleteHelper(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteHelper(node.right, studentId);
        } else {

            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            BSTNode minNode = getMin(node.right);
            node.data = minNode.data;
            node.right = deleteHelper(node.right, minNode.data.getStudentId());
        }
        return node;
    }

    private BSTNode getMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Student> getInOrderStudents() {
        List<Student> list = new ArrayList<>();
        inOrderHelper(root, list);
        return list;
    }

    private void inOrderHelper(BSTNode node, List<Student> list) {
        if (node == null) return;
        inOrderHelper(node.left, list);
        list.add(node.data);
        inOrderHelper(node.right, list);
    }

    public void printAllStudents() {
        List<Student> students = getInOrderStudents();
        System.out.println("學生索引資料");
        if (students.isEmpty()) {
            System.out.println("[ 空索引 ]");
        } else {
            for (Student s : students) {
                System.out.println("  " + s);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("StudentBstIndex");

        StudentBstIndex index = new StudentBstIndex();

        System.out.println("新增學生資料】");
        index.insert(new Student("S103", "Alice", "資工系"));
        index.insert(new Student("S101", "Bob", "資管系"));
        index.insert(new Student("S105", "Charlie", "電機系"));
        index.insert(new Student("S102", "David", "資工系"));
        index.insert(new Student("S104", "Eve", "企管系"));
        System.out.println();
        index.printAllStudents();

        System.out.println("\n 重複學號加入測試");
        index.insert(new Student("S103", "Duplicate Alice", "數學系"));


        System.out.println("\n 學號搜尋測試");
        Student found = index.search("S102");
        System.out.println("搜尋 S102 結果: " + (found != null ? found : "未找到"));

        Student notFound = index.search("S999");
        System.out.println("搜尋 S999 結果: " + (notFound != null ? notFound : "未找到"));

        System.out.println("\n 刪除學生資料測試");
        System.out.println("1. 刪除不存在學號 S999:");
        index.delete("S999");

        System.out.println("\n2. 刪除葉子節點 S102:");
        index.delete("S102");
        index.printAllStudents();

        System.out.println("\n3. 刪除雙子節點 (Root) S103:");
        index.delete("S103");
        index.printAllStudents();
    }
}
