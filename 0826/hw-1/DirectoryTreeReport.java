import java.util.ArrayList;
import java.util.List;

enum NodeType {
    FILE, DIRECTORY
}

class FileSystemNode {
    private String name;
    private NodeType type;
    private long size;
    private List<FileSystemNode> children;

    public FileSystemNode(String name) {
        this.name = name;
        this.type = NodeType.DIRECTORY;
        this.size = 0;
        this.children = new ArrayList<>();
    }

    public FileSystemNode(String name, long size) {
        this.name = name;
        this.type = NodeType.FILE;
        this.size = Math.max(0, size);
        this.children = new ArrayList<>();
    }

    public boolean addChild(FileSystemNode child) {
        if (this.type != NodeType.DIRECTORY) {
            System.out.println("錯誤,無法向檔案 [" + this.name + "] 新增子節點");
            return false;
        }
        this.children.add(child);
        return true;
    }

    public String getName() {
        return name;
    }

    public NodeType getType() {
        return type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<FileSystemNode> getChildren() {
        return children;
    }
}

public class DirectoryTreeReport {
  public static long calculateDirectorySizesPostOrder(FileSystemNode node) {
        if (node == null) return 0;

        if (node.getType() == NodeType.FILE) {
            return node.getSize();
        }

        long currentDirectoryTotalSize = 0;
        for (FileSystemNode child : node.getChildren()) {
            currentDirectoryTotalSize += calculateDirectorySizesPostOrder(child);
        }

        node.setSize(currentDirectoryTotalSize);
        return currentDirectoryTotalSize;
    }

    public static int countTotalNodes(FileSystemNode node) {
        if (node == null) return 0;
        int count = 1;
        for (FileSystemNode child : node.getChildren()) {
            count += countTotalNodes(child);
        }
        return count;
    }

    public static int countFiles(FileSystemNode node) {
        if (node == null) return 0;
        if (node.getType() == NodeType.FILE) return 1;

        int count = 0;
        for (FileSystemNode child : node.getChildren()) {
            count += countFiles(child);
        }
        return count;
    }

    public static int countDirectories(FileSystemNode node) {
        if (node == null) return 0;
        if (node.getType() == NodeType.FILE) return 0;

        int count = 1; 
        for (FileSystemNode child : node.getChildren()) {
            count += countDirectories(child);
        }
        return count;
    }

    public static int getHeight(FileSystemNode node) {
        if (node == null) return 0;
        int maxHeight = 0;
        for (FileSystemNode child : node.getChildren()) {
            maxHeight = Math.max(maxHeight, getHeight(child));
        }
        return 1 + maxHeight;
    }

    public static FileSystemNode findLargestFile(FileSystemNode node) {
        if (node == null) return null;

        FileSystemNode largest = null;
        if (node.getType() == NodeType.FILE) {
            largest = node;
        }

        for (FileSystemNode child : node.getChildren()) {
            FileSystemNode candidate = findLargestFile(child);
            if (candidate != null) {
                if (largest == null || candidate.getSize() > largest.getSize()) {
                    largest = candidate;
                }
            }
        }
        return largest;
    }

    public static void printTreeStructure(FileSystemNode node, String indent, boolean isLast) {
        if (node == null) return;

        String marker = isLast ? "└── " : "├── ";
        String typeIcon = (node.getType() == NodeType.DIRECTORY) ? "資料夾" : "文件";
        String sizeInfo = String.format("(%,d Bytes)", node.getSize());

        System.out.println(indent + marker + typeIcon + " " + node.getName() + " " + sizeInfo);

        List<FileSystemNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            boolean lastChild = (i == children.size() - 1);
            printTreeStructure(children.get(i), indent + (isLast ? "    " : "│   "), lastChild);
        }
    }

    public static void main(String[] args) {
        System.out.println("DirectoryTreeReport \n");

        FileSystemNode root = new FileSystemNode("root");

        FileSystemNode src = new FileSystemNode("src");
        src.addChild(new FileSystemNode("Main.java", 2500));
        src.addChild(new FileSystemNode("Utils.java", 1800));

        FileSystemNode assets = new FileSystemNode("assets");
        FileSystemNode images = new FileSystemNode("images");
        images.addChild(new FileSystemNode("logo.png", 1500000));
        images.addChild(new FileSystemNode("banner.jpg", 4200000));
        assets.addChild(images);
        assets.addChild(new FileSystemNode("config.json", 512));

        root.addChild(src);
        root.addChild(assets);
        root.addChild(new FileSystemNode("README.md", 1024));

        System.out.println("Post-order計算每個Directory大小");
        calculateDirectorySizesPostOrder(root);
        System.out.println("計算完成 n");

        System.out.println("檔案系統樹結構");
        printTreeStructure(root, "", true);
        System.out.println();

        System.out.println("Summary");
        System.out.printf("Total Nodes: %d 個%n", countTotalNodes(root));
        System.out.printf("File Count%d 個%n", countFiles(root));
        System.out.printf("Directory Count: %d 個%n", countDirectories(root));
        System.out.printf("Tree Height: %d%n", getHeight(root));
        System.out.printf("Total Size: %,d Bytes%n", root.getSize());

        FileSystemNode largestFile = findLargestFile(root);
        if (largestFile != null) {
            System.out.printf("Largest File: %s (%,d Bytes)%n",
                    largestFile.getName(), largestFile.getSize());
        } else {
            System.out.println("Largest File: 無");
        }
    }
}
