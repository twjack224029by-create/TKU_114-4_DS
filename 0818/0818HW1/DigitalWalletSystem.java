import java.util.Objects;

class DigitalWallet{
  private final String walletId;
  private final String owner;
  private double balance;
  private int totalTransactions;

  public DigitalWallet(String walletId,String owner,double initialBalance){
    if (walletId == null || walletId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID 不能為空");
        }
        if (owner == null || owner.trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能為空");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("初始餘額不能為負數");
        }
  }

  this.walletId=walletId;
  this.owner=owner;
  this.balance=initialBalance;
  this.totalTransactions=0;
}

public String getwalletId(){return Walletid;}
public String getowner(){return owner;}
public Double getBalance(){return balance;}
public int getTotalTransactions(){return totalTransactions;}

/*加錢*/
public boolean deposit(double amount){
  if(amount<=0){
    System.out.printf("加值金額必須大於 0");
    return false;
  }
  this.balance += amount;
  this.totalTransactions++;
  System.out.printf("成功加值 $%.2f, 當前餘額: $%2.f%n",amount,this.balance);
  return true;
}

/*付款*/
public boolean pay(double amount){
  if(amount<=0){
    System.out.println("付款金額必須大於 0");
    return false;
  }
  if(amount>this.balance){
    System.out.printf("餘額不足");
    return false;
  }
  this.balance -= amount; 
  this.totalTransactions++;
  System.out.printf("成功付款 金額: $%.2f, 當前餘額: $%2.f%n",amount,this.balance);
  return true;
}

/*退錢*/
public boolean refund(double amount){
  if(amount<=0){
    System.out.printf("退款金額必須大於 0");
    return false;
  }
  this.balance += amount; 
  this.totalTransactions++;
  System.out.printf("成功退款 金額: $%.2f, 當前餘額: $%2.f%n",amount,this.balance);
  return true;
}

/*帳號資料*/
public void printStatus(){
  System.out.printf("錢包id: %s 持有人: %s%n",Walletid,owner);
  System.out.printf("目前餘額: $%.2f 總交易次數: %d%n",balance,totalTransactions);
} 
}

public class DigitalWalletSystem{
  public static void main(String[] args){
    System.out.println("test start");

    DigitalWallet wallet = new DigitalWallet("W1001", "Alan", 1000.0);
    wallet.printStatus();

    System.out.println("\n 加值 500 元");
    wallet.deposit(500.0);

    System.out.println("\n 付款 300 元");
    wallet.pay(300.0);

    System.out.println("\n 付款失敗測試 餘額不足");
    wallet.pay(3000.0);

    System.out.println("\n 不合法輸入測試 負數與0");
    wallet.deposit(-100.0);
    wallet.pay(-50.0);
    wallet.refund(0.0);

    System.out.println("\n 退款 200 元");
    wallet.refund(200.0);

    System.out.println("\n 帳戶狀態");
    wallet.printStatus();
  }
}
