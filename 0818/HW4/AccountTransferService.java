import java.util.Objects;
class Account{
  private final String accountNumber;
  private final String ownerName;
  private int balance;

  public Account(String accountNumber, String ownerName, int initialBalance){
    if(accountNumber == null || accountNumber.trim().isEmpty()){throw new IllegalArgumentException("帳號名稱不得為空");}
    if(ownerName == null || ownerName.trim().isEmpty()){ throw new IllegalArgumentException("戶名不得為空");}
    if(initialBalance < 0){throw new IllegalArgumentException("初始餘額不得為負數");}
    this.accountNumber = accountNumber;
    this.ownerName = ownerName;
    this.balance = balance;
  }
  public String get(AccountNumber){return accountNumber;}
  public String get(OwnerName){return ownerName;}
  public int get(Balance){return balance;}

  void withdraw(int amount) {
        this.balance -= amount;
    }
  void deposit(int amount) {
        this.balance += amount;
    }
  @Override
    public String toString() {
        return String.format("帳號: %s  戶名: %s  餘額: $%d", accountNumber, ownerName, balance);
    }
}

class TransferService{
  public static boolean transfer(Account source, Account target, int amount){
    if(){}
    if(){}
    if(){}
    if(){}
    source.withdraw(amount);
    target.deposit(amount);

    System.out.printf("成功從 [%s] 轉帳 $%d 至 [%s]%n",
    source.getAccountNumber(), amount, target.getAccountNumber());
    return true;
  }
}



