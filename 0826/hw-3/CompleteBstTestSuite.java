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
