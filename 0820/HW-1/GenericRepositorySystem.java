import java.util.ArrayList;
import java.util.List;

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = Math.max(0, price);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Product[ID=%s, 名稱=%s, 價格=%.0f]", id, name, price);
    }
}

class Repository<T> {
    private List<T> items;

    public Repository() {
        this.items = new ArrayList<>();
    }

    public void add(T item) {
        if (item != null) {
            items.add(item);
            System.out.println("已新增: " + item);
        }
    }

    public T get(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        System.out.println("索引Out of Bounds: " + index);
        return null;
    }

    public boolean remove(T item) {
        boolean result = items.remove(item);
        if (result) {
            System.out.println("成功移除: " + item);
        } else {
            System.out.println("找不到刪除項目: " + item);
        }
        return result;
    }

    public T remove(int index) {
        if (index >= 0 && index < items.size()) {
            T removedItem = items.remove(index);
            System.out.println("成功移除 index [" + index + "]: " + removedItem);
            return removedItem;
        }
        System.out.println("無法移除，索引 Out of Bounds: " + index);
        return null;
    }

    public int size() {
        return items.size();
    }

    public void printAll() {
        System.out.println("Repository內容 (共 " + size() + " 筆)");
        if (items.isEmpty()) {
            System.out.println("(無資料)");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println(" [" + i + "] " + items.get(i));
            }
        }
    }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        System.out.println("Repository<T>test\n");

        System.out.println("test Repository<String>");
        Repository<String> stringRepo = new Repository<>();

        stringRepo.add("Java 程式設計");
        stringRepo.add("資料結構與演算法");
        stringRepo.add("雲端運算概論");
        stringRepo.printAll();

        System.out.println("索引1的內容: " + stringRepo.get(1));
        stringRepo.remove("雲端運算概論");
        stringRepo.printAll();

        System.out.println("test測試 Repository<Product>");
        Repository<Product> productRepo = new Repository<>();

        Product p1 = new Product("P001", "MacBook Pro 16", 79900);
        Product p2 = new Product("P002", "iPhone 16 Pro", 36900);
        Product p3 = new Product("P003", "AirPods Pro", 7490);

        productRepo.add(p1);
        productRepo.add(p2);
        productRepo.add(p3);
        productRepo.printAll();

        productRepo.remove(0); 
        productRepo.get(10);  

        productRepo.printAll();
    }
}
