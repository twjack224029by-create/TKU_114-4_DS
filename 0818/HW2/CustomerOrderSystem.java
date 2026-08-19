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
    this.customerId = customerId;
    this.name = name;
    this.email = email;
  }
  
}
