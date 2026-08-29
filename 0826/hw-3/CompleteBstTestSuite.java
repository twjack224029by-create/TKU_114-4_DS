import java.util.ArrayList;
import java.util.List;

class BinarySearchTree {
    static class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;

    public boolean insert(int key) {
        if (search(key)) return false; 
        root = insertRec(root, key);
        return true;
    }

    private Node insertRec(Node root, int key) {
        if (root == null) return new Node(key);
        if (key < root.key) root.left = insertRec(root.left, key);
        else if (key > root.key) root.right = insertRec(root.right, key);
        return root;
    }

    public boolean search(int key) {
        return searchRec(root, key);
    }

    private boolean searchRec(Node root, int key) {
        if (root == null) return false;
        if (root.key == key) return true;
        return key < root.key ? searchRec(root.left, key) : searchRec(root.right, key);
    }

    public boolean delete(int key) {
        if (!search(key)) return false;
        root = deleteRec(root, key);
        return true;
    }

    private Node deleteRec(Node root, int key) {
        if (root == null) return null;

        if (key < root.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.key) {
            root.right = deleteRec(root.right, key);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    private int minValue(Node root) {
        int minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    public List<Integer> rangeSearch(int min, int max) {
        List<Integer> result = new ArrayList<>();
        rangeRec(root, min, max, result);
        return result;
    }

    private void rangeRec(Node node, int min, int max, List<Integer> list) {
        if (node == null) return;
        if (node.key > min) rangeRec(node.left, min, max, list);
        if (node.key >= min && node.key <= max) list.add(node.key);
        if (node.key < max) rangeRec(node.right, min, max, list);
    }

    public List<Integer> inOrder() {
        List<Integer> list = new ArrayList<>();
        inOrderRec(root, list);
        return list;
    }

    private void inOrderRec(Node node, List<Integer> list) {
        if (node == null) return;
        inOrderRec(node.left, list);
        list.add(node.key);
        inOrderRec(node.right, list);
    }

    public boolean isBstInvariantValid() {
        List<Integer> keys = inOrder();
        for (int i = 0; i < keys.size() - 1; i++) {
            if (keys.get(i) >= keys.get(i + 1)) return false; 
        }
        return true;
    }

    public int size() {
        return inOrder().size();
    }

    public Node getRoot() {
        return root;
    }
}

public class CompleteBstTestSuite {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    private static void check(String description, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.printf("[ PASS ] Test %02d: %s%n", totalTests, description);
        } else {
            failedTests++;
            System.err.printf("[ FAIL ] Test %02d: %s%n", totalTests, description);
        }
    }

    public static void main(String[] args) {
        System.out.println("Complete Binary Search Tree Test Suite");

        BinarySearchTree bst = new BinarySearchTree();

        System.out.println("Empty Tree");
        check("Empty tree size should be 0", bst.size() == 0);
        check("Search on empty tree returns false", !bst.search(50));
        check("Delete on empty tree returns false", !bst.delete(50));
        check("Empty tree BST invariant holds true", bst.isBstInvariantValid());

        System.out.println("\n--- [ Category 2: Insert & Duplicate ] ---");
        check("Insert root (50)", bst.insert(50));
        check("Tree size is 1 after root insertion", bst.size() == 1);
        check("Insert left child (30)", bst.insert(30));
        check("Insert right child (70)", bst.insert(70));
        check("Insert duplicate key (50) returns false", !bst.insert(50));
        check("Insert duplicate key (30) returns false", !bst.insert(30));

        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        System.out.println("\nSearch & Missing");
        check("Search existing leaf node (20)", bst.search(20));
        check("Search existing internal node (30)", bst.search(30));
        check("Search non-existing key (99)", !bst.search(99));
        check("Search non-existing key (-10)", !bst.search(-10));

        System.out.println("\nRange Search & Invariant");
        check("BST invariant is valid", bst.isBstInvariantValid());
        List<Integer> rangeResult = bst.rangeSearch(25, 65);
        check("Range search [25, 65] count is correct (30, 40, 50, 60)", rangeResult.size() == 4);
        check("Range search contents valid", rangeResult.containsAll(List.of(30, 40, 50, 60)));

        System.out.println("\nNode Deletion Scenarios");

        check("Delete leaf node (20)", bst.delete(20));
        check("Node 20 no longer exists", !bst.search(20));
        check("BST invariant valid after deleting leaf", bst.isBstInvariantValid());

        bst.insert(25); 
        check("Delete node with one child (30)", bst.delete(30));
        check("Node 25 successfully re-linked", bst.search(25));

        check("Delete node with two children (70)", bst.delete(70));
        check("Node 70 no longer exists", !bst.search(70));
        check("Children (60, 80) remain accessible", bst.search(60) && bst.search(80));

        check("Delete root node (50)", bst.delete(50));
        check("Old root (50) is gone", !bst.search(50));
        check("New root replaced and BST invariant valid", bst.isBstInvariantValid());

        System.out.printf("  Test Execution Summary: Total: %d | PASS: %d | FAIL: %d%n",
                totalTests, passedTests, failedTests);

        if (failedTests == 0) {
            System.out.println("ALL BST TEST ASSERTIONS PASSED!");
        } else {
            System.err.println("SOME TESTS FAILED. PLEASE CHECK LOGS ABOVE.");
        }
    }
}
