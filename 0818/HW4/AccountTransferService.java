import java.util.Objects;

class Account {
    private final String accountNumber;
    private final String ownerName;
    private int balance;

    public Account(String accountNumber, String ownerName, int initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("帳號名稱不得為空");
        }
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new IllegalArgumentException("戶名不得為空");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("初始餘額不得為負數");
        }

        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getBalance() {
        return balance;
    }

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

class TransferService {

    public static boolean transfer(Account source, Account target, int amount) {

        if (source == null || target == null) {
            System.out.println("失敗,轉出或轉入帳戶不得為 null");
            return false;
        }

        if (source == target) {
            System.out.println("失敗,無法轉帳給相同的帳戶");
            return false;
        }

        if (amount <= 0) {
            System.out.printf("失敗,轉帳金額無效 ($%d)，金額必須大於 0%n", amount);
            return false;
        }

        if (source.getBalance() < amount) {
            System.out.printf("失敗,轉出帳戶餘額不足 (當前餘額: $%d, 欲轉帳金額: $%d)%n",
                    source.getBalance(), amount);
            return false;
        }

        source.withdraw(amount);
        target.deposit(amount);

        System.out.printf("成功從 [%s] 轉帳 $%d 至 [%s]%n",
                source.getAccountNumber(), amount, target.getAccountNumber());
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        System.out.println("test start \n");

        Account acc1 = new Account("ACT-001", "Alice", 1000);
        Account acc2 = new Account("ACT-002", "Bob", 500);

        System.out.println("帳戶狀態");
        System.out.println("帳戶 A: " + acc1);
        System.out.println("帳戶 B: " + acc2);

        System.out.println("\n 轉帳 $300 (Alice -> Bob)");
        boolean result1 = TransferService.transfer(acc1, acc2, 300);
        System.out.println("轉帳結果: " + (result1 ? "成功" : "失敗"));
        System.out.println("帳戶 A: " + acc1);
        System.out.println("帳戶 B: " + acc2);

        System.out.println("\n 餘額不足轉帳");
        boolean result2 = TransferService.transfer(acc1, acc2, 2000);
        System.out.println("轉帳結果: " + (result2 ? "成功" : "失敗"));
        System.out.println("帳戶 A ($保持不變): " + acc1);
        System.out.println("帳戶 B ($保持不變): " + acc2);

        System.out.println("\n 同帳戶轉帳");
        boolean result3 = TransferService.transfer(acc1, acc1, 100);
        System.out.println("轉帳結果: " + (result3 ? "成功" : "失敗"));
        System.out.println("帳戶 A ($保持不變): " + acc1);

        System.out.println("\n 空帳號");
        boolean result4 = TransferService.transfer(acc1, null, 100);
        System.out.println("轉帳結果: " + (result4 ? "成功" : "失敗"));
        System.out.println("帳戶 A ($保持不變): " + acc1);

        System.out.println("\n 轉帳無效金額");
        boolean result5 = TransferService.transfer(acc1, acc2, -50);
        System.out.println("轉帳結果: " + (result5 ? "成功" : "失敗"));
        System.out.println("帳戶 A ($保持不變): " + acc1);

        System.out.println("帳戶狀態");
        System.out.println("帳戶 A: " + acc1);
        System.out.println("帳戶 B: " + acc2);
    }
}
