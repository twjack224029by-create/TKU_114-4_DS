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
