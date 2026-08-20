import java.util.Objects;

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private double balance;
    private int totalTransactions;

    public DigitalWallet(String walletId, String owner, double initialBalance) {
        if (walletId == null || walletId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID 不能為空");
        }
        if (owner == null || owner.trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能為空");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("初始餘額不能為負數");
        }

        this.walletId = walletId;
        this.owner = owner;
        this.balance = initialBalance;
        this.totalTransactions = 0;
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

    public int getTotalTransactions() {
        return totalTransactions;
    }

    /*加錢*/
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("失敗,儲值金額必須大於 0");
            return false;
        }
        this.balance += amount;
        this.totalTransactions++;
        System.out.printf("成功儲值 $%.2f，當前餘額: $%.2f%n", amount, this.balance);
        return true;
    }

    /*付錢*/
    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("失敗,付款金額必須大於 0");
            return false;
        }
        if (amount > this.balance) {
            System.out.printf("失敗,餘額不足 嘗試扣款 $%.2f，但當前餘額僅 $%.2f%n", amount, this.balance);
            return false;
        }
        this.balance -= amount;
        this.totalTransactions++;
        System.out.printf("成功扣款 $%.2f，當前餘額: $%.2f%n", amount, this.balance);
        return true;
    }

    /*退錢*/
    public boolean refund(double amount) {
        if (amount <= 0) {
            System.out.println("失敗,退款金額必須大於 0");
            return false;
        }
        this.balance += amount;
        this.totalTransactions++;
        System.out.printf("成功退款 $%.2f，當前餘額: $%.2f%n", amount, this.balance);
        return true;
    }

    // 顯示帳戶
    public void printStatus() {
        System.out.printf("錢包 ID: %s  持有者: %s%n", walletId, owner);
        System.out.printf("目前餘額: $%.2f  總成功交易次數: %d%n", balance, totalTransactions);
    }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        System.out.println("test start");
        
        DigitalWallet wallet = new DigitalWallet("W1001", "Alan", 1000.0);
        wallet.printStatus();

        System.out.println("\n 儲值 500 元");
        wallet.deposit(500.0);

        System.out.println("\n 付款 300 元");
        wallet.pay(300.0);

        // 餘額不足
        System.out.println("\n 付款失敗餘額不足");
        wallet.pay(3000.0);

        //負數金額與不法輸入
        System.out.println("\n 不法金額:負數與零");
        wallet.deposit(-100.0);
        wallet.pay(-50.0);
        wallet.refund(0.0);

        System.out.println("\n 退款 200 元");
        wallet.refund(200.0);

        //帳戶狀態
        System.out.println("\n 帳戶狀態");
        wallet.printStatus();
    }
}
