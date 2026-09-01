class Book {
    private String isbn;
    private String title;
    private String author;
    private int price;

    public Book(String isbn, String title, String author, int price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPrice() { return price; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPrice(int price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("ISBN: %-13s  書名: %-18s  作者: %-10s  價格: NT$%d",
                isbn, title, author, price);
    }
}

class BookNode {
    Book book;
    BookNode next;

    public BookNode(Book book) {
        this.book = book;
        this.next = null;
    }
}

public class BookIsbnHashTable {
  private BookNode[] buckets; 
    private int size;           
    private int capacity;      

    public BookIsbnHashTable() {
        this(7);
    }

    public BookIsbnHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Bucket容量必須大於0");
        }
        this.capacity = capacity;
        this.buckets = new BookNode[capacity];
        this.size = 0;
    }

    private int hash(String isbn) {
        return Math.abs(isbn.hashCode()) % capacity;
    }

    public void put(Book book) {
        if (book == null || book.getIsbn() == null) {
            System.out.println("失敗,無效的書籍資料或ISBN為空");
            return;
        }

        String isbn = book.getIsbn();
        int bucketIndex = hash(isbn);
        BookNode current = buckets[bucketIndex];

        while (current != null) {
            if (current.book.getIsbn().equals(isbn)) {
                current.book.setTitle(book.getTitle());
                current.book.setAuthor(book.getAuthor());
                current.book.setPrice(book.getPrice());
                System.out.println("ISBN: " + isbn + " 之書籍內容已更新");
                return;
            }
            current = current.next;
        }

        BookNode newNode = new BookNode(book);
        newNode.next = buckets[bucketIndex];
        buckets[bucketIndex] = newNode;
        size++;
        System.out.println("新增成功 " + book);
    }

    public Book get(String isbn) {
        if (isbn == null) return null;
        int bucketIndex = hash(isbn);
        BookNode current = buckets[bucketIndex];

        while (current != null) {
            if (current.book.getIsbn().equals(isbn)) {
                return current.book;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(String isbn) {
        if (isbn == null) return false;
        int bucketIndex = hash(isbn);
        BookNode current = buckets[bucketIndex];
        BookNode prev = null;

        while (current != null) {
            if (current.book.getIsbn().equals(isbn)) {
                if (prev == null) {
                    buckets[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                System.out.println("已自索引中移除 《" + current.book.getTitle() + "》 (ISBN: " + isbn + ")");
                return true;
            }
            prev = current;
            current = current.next;
        }

        System.out.println("失敗,查無ISBN為 " + isbn + " 的書籍。");
        return false;
    }

    public int size() {
        return size;
    }

    public double getLoadFactor() {
        return (double) size / capacity;
    }

    public void bucketReport() {
        System.out.printf("                   圖書ISBN Hash Table結構報表 (Capacity: %d  Size: %d)%n", capacity, size);
        System.out.printf("                   當前Load Factor: %.2f%n", getLoadFactor());

        int activeBuckets = 0;
        int maxChainLength = 0;

        for (int i = 0; i < capacity; i++) {
            System.out.printf(" Bucket %02d ➔ ", i);
            BookNode current = buckets[i];

            if (current == null) {
                System.out.println("(Empty)");
            } else {
                activeBuckets++;
                int chainLength = 0;
                StringBuilder sb = new StringBuilder();

                while (current != null) {
                    sb.append("《").append(current.book.getTitle()).append("》(").append(current.book.getIsbn()).append(") -> ");
                    current = current.next;
                    chainLength++;
                }
                sb.append("null");
                if (chainLength > maxChainLength) {
                    maxChainLength = chainLength;
                }
                System.out.printf("%s (鏈長度: %d)%n", sb.toString(), chainLength);
            }
        }

        System.out.printf(" 摘要: Bucket 使用率 = %d/%d (%.2f%%)  最長衝突鏈長度 = %d%n",
                activeBuckets, capacity, (activeBuckets * 100.0 / capacity), maxChainLength);
    }

    public static void main(String[] args) {
        BookIsbnHashTable table = new BookIsbnHashTable(5);

        System.out.println("put / insert ");
        table.put(new Book("101", "Java 程式設計聖經", "Gosling", 680));
        table.put(new Book("102", "資料結構實戰", "Knuth", 550));
        table.put(new Book("103", "演算法視覺化", "Cormen", 720));
        table.put(new Book("104", "Clean Code 代碼潔癖", "Martin", 600));
        table.put(new Book("105", "作業系統系統化", "Silberschatz", 800));

        table.bucketReport();

        System.out.println("測試重複 ISBN 更新機制 (put / update)");
        System.out.println("更新前 Size: " + table.size());
        table.put(new Book("101", "Java 程式設計聖經 (第2版)", "Gosling", 750));
        System.out.println("更新後Size: " + table.size() + " (驗證Size不增加)\n");

        System.out.println("測試搜尋功能");
        Book searchedBook = table.get("101");
        System.out.println("搜尋結果 (101): " + searchedBook);

        Book notFoundBook = table.get("001");
        System.out.println("搜尋結果 (不存在 ISBN): " + notFoundBook + "\n");

        System.out.println("測試刪除功能");
        table.remove("103"); 
        table.remove("001"); 

        System.out.println("\n最終Hash Table");
        table.bucketReport();
    }
}
