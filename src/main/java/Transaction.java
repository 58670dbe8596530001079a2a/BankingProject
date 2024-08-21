import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime time;
    private AccountCurrency currency;
    private String type;
    private BigDecimal amount;

    public Transaction(AccountCurrency currency, LocalDateTime time, BigDecimal amount, String type) {
        this.currency = currency;
        this.time = time;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "time=" + time +
                ", currency=" + currency +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                '}';
    }
}
