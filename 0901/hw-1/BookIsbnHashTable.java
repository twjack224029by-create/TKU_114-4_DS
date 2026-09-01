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
  
}
