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








