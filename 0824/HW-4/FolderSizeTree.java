import java.util.ArrayList;
import java.util.List;

class FolderNode {
    String name;          
    long ownSize;        
    FolderNode left;      
    FolderNode right;     

    public FolderNode(String name, long ownSize) {
        this.name = name;
        this.ownSize = ownSize;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return name + " (ownSize: " + ownSize + " B)";
    }
}

public class FolderSizeTree {

    private FolderNode maxSubtreeNode = null;
    private long maxSubtreeSize = -1;

    public long calculateAndPrintSizes(FolderNode node) {
        if (node == null) {
            return 0;
        }

        long leftTotal = calculateAndPrintSizes(node.left);
        long rightTotal = calculateAndPrintSizes(node.right);

        long totalSize = node.ownSize + leftTotal + rightTotal;

        System.out.printf("目錄 [%-12s]: 本身 = %-5d B, 子樹總和 = %-6d B%n",
                node.name, node.ownSize, totalSize);

        if (totalSize > maxSubtreeSize) {
            maxSubtreeSize = totalSize;
            maxSubtreeNode = node;
        }

        return totalSize;
    }

    public List<FolderNode> findLeafFolders(FolderNode node) {
        List<FolderNode> leaves = new ArrayList<>();
        findLeafFoldersHelper(node, leaves);
        return leaves;
    }

    private void findLeafFoldersHelper(FolderNode node, List<FolderNode> leaves) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            leaves.add(node);
            return;
        }

        findLeafFoldersHelper(node.left, leaves);
        findLeafFoldersHelper(node.right, leaves);
    }

    public static void main(String[] args) {
        System.out.println("FolderSizeTree");

        FolderNode root = new FolderNode("root", 100);
        root.left = new FolderNode("docs", 200);
        root.right = new FolderNode("media", 500);

        root.left.left = new FolderNode("pdf", 50);
        root.left.right = new FolderNode("txt", 10);

        root.right.left = new FolderNode("images", 800);

        FolderSizeTree treeCalculator = new FolderSizeTree();

        System.out.println("Post-order計算各目錄Subtree大小");
        long totalRootSize = treeCalculator.calculateAndPrintSizes(root);

        System.out.println("統計摘要");
        System.out.println("Root Total Size : " + totalRootSize + " Bytes");

        if (treeCalculator.maxSubtreeNode != null) {
            System.out.println("Max Subtree Folder : " 
                    + treeCalculator.maxSubtreeNode.name 
                    + " (總計 " + treeCalculator.maxSubtreeSize + " Bytes)");
        }

        System.out.println("\n Leaf Folders列表");
        List<FolderNode> leafFolders = treeCalculator.findLeafFolders(root);
        for (FolderNode leaf : leafFolders) {
            System.out.println(leaf);
        }
    }
}
