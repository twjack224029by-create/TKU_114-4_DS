import java.util.ArrayList;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

class BinarySearchTree {
    private TreeNode root;
    private int count;

    public BinarySearchTree() {
        this.root = null;
        this.count = 0;
    }

    public TreeNode getRoot() {
        return root;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean insert(int val) {
        int initialSize = count;
        root = insertHelper(root, val);
        return count > initialSize;
    }

    private TreeNode insertHelper(TreeNode node, int val) {
        if (node == null) {
            count++;
            return new TreeNode(val);
        }
        if (val < node.val) {
            node.left = insertHelper(node.left, val);
        } else if (val > node.val) {
            node.right = insertHelper(node.right, val);
        }
        return node;
    }

    public boolean delete(int val) {
        if (isEmpty()) {
            return false;
        }
        int initialSize = count;
        root = deleteHelper(root, val);
        return count < initialSize;
    }

    private TreeNode deleteHelper(TreeNode node, int val) {
        if (node == null) {
            return null;
        }

        if (val < node.val) {
            node.left = deleteHelper(node.left, val);
        } else if (val > node.val) {
            node.right = deleteHelper(node.right, val);
        } else {
            count--;

            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            TreeNode successor = getMinNode(node.right);
            node.val = successor.val;
            count++; 
            node.right = deleteHelper(node.right, successor.val);
        }
        return node;
    }

    private TreeNode getMinNode(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Integer> toInOrderList() {
        List<Integer> list = new ArrayList<>();
        inOrderHelper(root, list);
        return list;
    }

    private void inOrderHelper(TreeNode node, List<Integer> list) {
        if (node == null) return;
        inOrderHelper(node.left, list);
        list.add(node.val);
        inOrderHelper(node.right, list);
    }
}
