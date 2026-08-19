import java.util.Objects;

class DigitalWallet{
  private final String Walletid;
  private final String owner;
  private double balance;
  private int totalTransactions;

  public DigitalWallet(String Walletid,String owner,double initialBalance){
    if(Walletid == null || Walletid.trim().isEmpty()){throw new IllegalArgumentException("請填寫id");}
    if(owner == null || owner.trim().isEmpty()){throw new IllegalArgumentException("請輸入姓名");)}
    if(initialbalance<0){throw new IllegalArgumentException("初始餘額不可為負");}
  }

  this.Walletid=Walletid;
  this.owner=owner;
  this.balance=initialBalance;
  this.totalTransactions=0;
}

public String getWalletid(){return Walletid;}
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
