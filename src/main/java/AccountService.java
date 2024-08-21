import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountService {
    private final HashMap<AccountCurrency, BigDecimal> balance;
    private final java.util.ArrayList<Transaction> transactionHistory;

    public AccountService() {
       this.balance = new HashMap<>();
       this.transactionHistory = new ArrayList<>();
    }

    public void withdraw(AccountCurrency accountCurrency, BigDecimal amountToWithdraw) throws NotEnoughBalanceException {
        if (amountToWithdraw.compareTo(balance.getOrDefault(accountCurrency, BigDecimal.ZERO)) > 0) {
            throw new NotEnoughBalanceException();
        }
        balance.put(accountCurrency,balance.get(accountCurrency).subtract(amountToWithdraw));
        recordTransaction(accountCurrency, amountToWithdraw, "WITHDRAW");

    }

    public void deposit(AccountCurrency accountCurrency, BigDecimal amountToDeposit) {
        if (amountToDeposit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
        balance.put(accountCurrency,balance.getOrDefault(accountCurrency, BigDecimal.ZERO).add(amountToDeposit));
        recordTransaction(accountCurrency, amountToDeposit, "DEPOSIT");

    }


    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public BigDecimal getBalanceFor(AccountCurrency currency) {
        return balance.getOrDefault(currency, BigDecimal.ZERO);
    }

    public void convert(AccountCurrency from, AccountCurrency to, BigDecimal amountToConvert) throws NotEnoughBalanceException {
        if (amountToConvert.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        if (amountToConvert.compareTo(balance.getOrDefault(from, BigDecimal.ZERO)) > 0) {
            throw new NotEnoughBalanceException();
        }
        this.withdraw(from, amountToConvert);
        this.deposit(to, amountToConvert.multiply(BigDecimal.valueOf(1.31)).stripTrailingZeros());

    }

    private void recordTransaction(AccountCurrency currency, BigDecimal amount, String type)
    {
        transactionHistory.add(new Transaction(currency, LocalDateTime.now(), amount, type));
    }

}
