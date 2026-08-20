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
