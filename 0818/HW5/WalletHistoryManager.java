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

  public boolean transferTo(Wallet target, double amount) {
        if (target == null || target == this || amount <= 0 || amount > this.balance) {
            System.out.println("失敗,轉帳條件不符 (無效金額、目標錯誤或餘額不足)");
            return false;
        }

        if (this.transactionCount >= this.transactions.length) {
            System.out.printf("失敗,來源錢包 [%s] 交易紀錄已滿%n", this.walletId);
            return false;
        }
        if (target.transactionCount >= target.transactions.length) {
            System.out.printf("失敗,目標錢包 [%s] 交易紀錄已滿%n", target.walletId);
            return false;
        }

        this.balance -= amount;
        target.balance += amount;

        this.recordTransaction("TRANSFER_OUT", amount);
        target.recordTransaction("TRANSFER_IN", amount);

        System.out.printf("成功從 [%s] 轉出 $%.2f 至 [%s]%n", this.owner, amount, target.owner);
        return true;
    }

  public Transaction findTransaction(int sequence) {
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getSequence() == sequence) {
                return transactions[i];
            }
        }
        return null;
    }
  
  public double totalByType(String type) {
        if (type == null) return 0.0;
        double total = 0.0;
        for (int i = 0; i < transactionCount; i++) {
            if (type.equalsIgnoreCase(transactions[i].getType())) {
                total += transactions[i].getAmount();
            }
        }
        return total;
   }

  public void printStatement() {
        System.out.printf("             對 帳 單             %n");
        System.out.printf("錢包 ID: %-10s  持有者: %s%n", walletId, owner);
        System.out.printf("當前餘額: $%.2f  總交易筆數: %d / %d%n", balance, transactionCount, transactions.length);
        System.out.println("交易明細列印:");
        
        if (transactionCount == 0) {
            System.out.println("  (無任何交易紀錄)");
        } else {
            for (int i = 0; i < transactionCount; i++) {
                System.out.println(transactions[i]);
            }
        }
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        System.out.println("test \n");

        Wallet walletA = new Wallet("W-A101", "Alice", 1000.0, 4);
        Wallet walletB = new Wallet("W-B202", "Bob", 500.0, 4);

        walletA.deposit(500.0);
        walletA.pay(200.0);

        walletA.transferTo(walletB, 300.0);

        System.out.println("\n test findTransaction ");
        Transaction t2 = walletA.findTransaction(2);
        if (t2 != null) {
            System.out.println("找到序號 #2 交易: " + t2);
        } else {
            System.out.println("找不到序號 #2 的交易");
        }

        Transaction t99 = walletA.findTransaction(99);
        System.out.println("搜尋序號 #99 結果: " + (t99 != null ? t99 : "null (未找到)"));

        System.out.println("\n test totalByType ");
        System.out.printf("Alice 的 DEPOSIT 總金額: $%.2f%n", walletA.totalByType("DEPOSIT"));
        System.out.printf("Alice 的 PAY 總金額: $%.2f%n", walletA.totalByType("PAY"));
        System.out.printf("Alice 的 TRANSFER_OUT 總金額: $%.2f%n", walletA.totalByType("TRANSFER_OUT"));

        System.out.println("\n test容量max,邊界保護");
        walletA.refund(100.0); 
        
        double balanceBefore = walletA.getBalance();
        System.out.printf("第 5 筆交易前餘額: $%.2f%n", balanceBefore);

        boolean paySuccess = walletA.pay(50.0);
        System.out.printf("第 5 筆付款嘗試結果: %s | 扣款後餘額: $%.2f%n", 
                paySuccess ? "成功" : "失敗 (遭到拒絕)", walletA.getBalance());

        System.out.println("\n");
        walletA.printStatement();
        walletB.printStatement();
    }
}



