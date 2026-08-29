import java.util.ArrayList;
import java.util.List;

class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true; 
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("[ISBN: %-13s  書名: %-20s  作者: %-12s  狀態: %s]",
                isbn, title, author, available ? "可借閱 (Available)" : "已借出 (Borrowed)");
    }
}

class BookNode {
    Book book;
    BookNode left;
    BookNode right;

    public BookNode(Book book) {
        this.book = book;
        this.left = null;
        this.right = null;
    }
}

public class LibraryBookBst {
    private BookNode root;

    public LibraryBookBst() {
        this.root = null;
    }

    public boolean add(Book book) {
        if (book == null || book.getIsbn() == null) return false;
        if (find(book.getIsbn()) != null) {
            System.out.println("[新增失敗] ISBN " + book.getIsbn() + " 已存在於系統中！");
            return false;
        }
        root = addRecursive(root, book);
        System.out.println("[新增成功] " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")");
        return true;
    }

    private BookNode addRecursive(BookNode current, Book book) {
        if (current == null) {
            return new BookNode(book);
        }

        int cmp = book.getIsbn().compareTo(current.book.getIsbn());
        if (cmp < 0) {
            current.left = addRecursive(current.left, book);
        } else if (cmp > 0) {
            current.right = addRecursive(current.right, book);
        }
        return current;
    }

    public Book find(String isbn) {
        BookNode node = findRecursive(root, isbn);
        return (node != null) ? node.book : null;
    }

    private BookNode findRecursive(BookNode current, String isbn) {
        if (current == null) return null;

        int cmp = isbn.compareTo(current.book.getIsbn());
        if (cmp == 0) {
            return current;
        } else if (cmp < 0) {
            return findRecursive(current.left, isbn);
        } else {
            return findRecursive(current.right, isbn);
        }
    }

    public boolean borrowBook(String isbn) {
        Book book = find(isbn);
        if (book == null) {
            System.out.println("失敗,查無 ISBN 為 " + isbn + " 的館藏書籍。");
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("失敗,《" + book.getTitle() + "》 目前已被借出。");
            return false;
        }
        book.setAvailable(false);
        System.out.println("成功,您已成功借閱 《" + book.getTitle() + "》。");
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = find(isbn);
        if (book == null) {
            System.out.println("失敗,查無 ISBN 為 " + isbn + " 的館藏記錄。");
            return false;
        }
        if (book.isAvailable()) {
            System.out.println("失敗,《" + book.getTitle() + "》 已在館內，無需歸還。");
            return false;
        }
        book.setAvailable(true);
        System.out.println("成功,《" + book.getTitle() + "》 已完成歸還程序。");
        return true;
    }

    public boolean remove(String isbn) {
        Book book = find(isbn);
        if (book == null) {
            System.out.println("失敗,查無 ISBN 為 " + isbn + " 的書籍。");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("失敗,ISBN " + isbn + " (《" + book.getTitle() + "》) 目前為借出狀態，不可刪除");
            return false;
        }

        root = removeRecursive(root, isbn);
        System.out.println("成功,已將 《" + book.getTitle() + "》 (ISBN: " + isbn + ") 自索引中移除。");
        return true;
    }

    private BookNode removeRecursive(BookNode current, String isbn) {
        if (current == null) return null;

        int cmp = isbn.compareTo(current.book.getIsbn());
        if (cmp < 0) {
            current.left = removeRecursive(current.left, isbn);
        } else if (cmp > 0) {
            current.right = removeRecursive(current.right, isbn);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            BookNode minNode = findMin(current.right);
            current.book = minNode.book;
            current.right = removeRecursive(current.right, minNode.book.getIsbn());
        }
        return current;
    }

    private BookNode findMin(BookNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Book> rangeQuery(String startIsbn, String endIsbn) {
        List<Book> result = new ArrayList<>();
        rangeQueryRecursive(root, startIsbn, endIsbn, result);
        return result;
    }

    private void rangeQueryRecursive(BookNode current, String start, String end, List<Book> result) {
        if (current == null) return;

        int cmpStart = current.book.getIsbn().compareTo(start);
        int cmpEnd = current.book.getIsbn().compareTo(end);

        if (cmpStart > 0) {
            rangeQueryRecursive(current.left, start, end, result);
        }

        if (cmpStart >= 0 && cmpEnd <= 0) {
            result.add(current.book);
        }

        if (cmpEnd < 0) {
            rangeQueryRecursive(current.right, start, end, result);
        }
    }

    public void printInorderReport() {
        System.out.println("館藏目錄表");
        if (root == null) {
            System.out.println("  (目前館藏為空)");
        } else {
            inorderRecursive(root);
        }
    }

    private void inorderRecursive(BookNode current) {
        if (current != null) {
            inorderRecursive(current.left);
            System.out.println("  " + current.book);
            inorderRecursive(current.right);
        }
    }

    public static void main(String[] args) {
        LibraryBookBst library = new LibraryBookBst();

        System.out.println("初始化並建立圖書館藏 (Insert Books) ");
        library.add(new Book("123", "Java 程式設計聖經", "James Gosling"));
        library.add(new Book("456", "資料結構與演算法", "Donald Knuth"));
        library.add(new Book("100", "演算法圖鑑", "石田"));
        library.add(new Book("789", "Clean Code 代碼潔癖", "Robert C. Martin"));
        library.add(new Book("300", "作業系統原理", "Silberschatz"));

        library.printInorderReport();

        System.out.println("測試借閱Borrowing System");
        library.borrowBook("456"); 
        library.borrowBook("456"); 

        System.out.println("\n測試刪除約束 (Remove Restrictions)");
        library.remove("456");
        library.remove("100");

        System.out.println("\n測試歸還 (Return System)");
        library.returnBook("456"); 
        library.remove("456"); 

        System.out.println("\n測試範圍查詢 (Range Query: 200 ~ 800)");
        List<Book> rangeBooks = library.rangeQuery("200", "800");
        for (Book b : rangeBooks) {
            System.out.println(" [範圍搜尋命中]" + b);
        }

        System.out.println("\n館藏序報表");
        library.printInorderReport();
    }
}
