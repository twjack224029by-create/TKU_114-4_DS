import java.util.Arrays;

class customer{
  private final String customerId;
  private final String name;
  private final String email;

  public Customer(String customerId,String name, String email){
    this.customerId = customerId;
    this.name = name;
    this.email = email;
  }
  public String getCustomerId(){return customerId;}
  public String getName(){return name;}
  public String getEmail(){return email;}
}

class OrderItem{
  private final Double itemName;
  private final String price;
  private final int quantity;

  public  OrderItem(Double itemName,String price, String quantity){
    if(price < 0 || quantity <= 0){throw new IllegalArgumentException("價格不能為負且數量必須大於 0");}
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }
  public String getItemName(){return itemName;}
  public Double getPrice(){return price;}
  public String getQuantity(){return quantity;}

  public Double getSubtotal(){return price * quantity;}
}

class CustomerOrder{
  private final String orderId; 
  private final Customer customer;
  private final OrderItem[] items;
  private int itemCount;

  public CustomerOrder(String orderId, Customer customer, int maxItems){
  if(customer == null){throw new IllegalArgumentException("訂單必須包含有效顧客資料");}
  if(maxItems <= 0){throw new IllegalArgumentException("訂單品項容量必須大於 0");}
  this.orderId = orderId;
  this.customer = customer;
  this.items = new OrderItem[maxItems];
  this.itemCount = 0;
  }
}

public boolean addItem(OrderItem item){
  if(item == null){
      System.out.println("失敗,品項不能為空");
      return false;
  }
  if(itemCount >= items.length){
      System.out.println("失敗,訂單容量已滿，無法新增品項: " + item.getItemName());
      return false;
  }
  items[itemCount] = item;
  itemCount++;
  return true;
}

 public double calculateTotalAmount() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getSubtotal();
        }
        return total;
    }

public int calculateTotalQuantity() {
        int totalQty = 0;
        for (int i = 0; i < itemCount; i++) {
            totalQty += items[i].getQuantity();
        }
        return totalQty;
    }





