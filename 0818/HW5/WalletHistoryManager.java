import java.util.Objects;
class Transaction {
  private final int sequence;
  private final String type;
  private final double amount;

  public Transaction(int sequence, String type, double amount) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
    }
  public int getSequence() {return sequence;}
  public String getType() {return type;}
  public double getAmount() {return amount;}

    @Override
    public String toString() {
        return String.format("  [序號 #%02d] 類型: %-12s | 金額: $%8.2f", sequence, type, amount);
    }
}
class Wallet{
  private final String walletId;
  private final String owner;
  private double balance;
  private final Transaction[] transactions;
  private int transactionCount; 
  private int globalSequence;

  public Wallet(String walletId, String owner, double initialBalance, int maxTransactions){
    if (walletId == null || walletId.trim().isEmpty()) {
            throw new IllegalArgumentException(" ID 不能為空");
        }
        if (owner == null || owner.trim().isEmpty()) {
            throw new IllegalArgumentException("擁有者名稱不能為空");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("初始餘額不能為負");
        }
        if (maxTransactions <= 0) {
            throw new IllegalArgumentException("交易紀錄上限必須大於 0");
        }
    this.walletId = walletId;
    this.owner = owner;
    this.balance = initialBalance;
    this.transactions = new Transaction[maxTransactions];
    this.transactionCount = 0;
    this.globalSequence = 1;
  }
  public String getWalletId() {
        return walletId;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

  private boolean recordTransaction(String type, double amount) {
        if (transactionCount >= transactions.length) {
            System.out.printf("失敗,錢包 [%s] 的交易紀錄容量已滿，無法寫入新紀錄！%n", walletId);
            return false;
        }
        transactions[transactionCount] = new Transaction(globalSequence++, type, amount);
        transactionCount++;
        return true;
    }

  public boolean deposit(double amount) {
        if (amount <= 0) return false;
        if (transactionCount >= transactions.length) {
            System.out.printf("失敗,錢包 [%s] 紀錄容量已滿，不得修改餘額%n", walletId);
            return false;
        }
        this.balance += amount;
        recordTransaction("DEPOSIT", amount);
        return true;
    }
  public boolean pay(double amount) {
        if (amount <= 0 || amount > balance) return false;
        if (transactionCount >= transactions.length) {
            System.out.printf("失敗,錢包 [%s] 紀錄容量已滿，不得修改餘額%n", walletId);
            return false;
        }
        this.balance -= amount;
        recordTransaction("PAY", amount);
        return true;
    }

  public boolean refund(double amount) {
        if (amount <= 0) return false;
        if (transactionCount >= transactions.length) {
            System.out.printf("失敗,錢包 [%s] 紀錄容量已滿，不得修改餘額%n", walletId);
            return false;
        }
        this.balance += amount;
        recordTransaction("REFUND", amount);
        return true;
    }
}



