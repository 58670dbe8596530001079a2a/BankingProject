import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class AccountServiceTest {

    @Test
    public void canDepositAndWithdrawTenPounds() throws NotEnoughBalanceException {
        var accountService = new AccountService();
        assertEquals(accountService.getBalanceFor(AccountCurrency.GBP), BigDecimal.ZERO);
        accountService.deposit(AccountCurrency.GBP, new BigDecimal("10.00"));
        accountService.withdraw(AccountCurrency.GBP, new BigDecimal("10.00"));
        assertEquals(accountService.getBalanceFor(AccountCurrency.GBP), new BigDecimal("0.00"));

    }
    @Test
    public void canNotWithdrawFifteenPounds() {
        var accountService = new AccountService();
        assertThrows(NotEnoughBalanceException.class, () -> accountService.withdraw(AccountCurrency.GBP, new BigDecimal("15.00")));

    }
    @Test
    public void canNotDepositZeroPounds() {
        var accountService = new AccountService();
        assertThrows(IllegalArgumentException.class, () -> accountService.deposit(AccountCurrency.GBP, BigDecimal.ZERO));

    }
    @Test
    public void canConvertFifteenPoundsToDollars() throws NotEnoughBalanceException {
        var accountService = new AccountService();
        assertEquals(accountService.getBalanceFor(AccountCurrency.GBP), BigDecimal.ZERO);
        accountService.deposit(AccountCurrency.GBP, new BigDecimal("15.00"));
        accountService.convert(AccountCurrency.GBP,AccountCurrency.USD, new BigDecimal("15.00"));
        assertEquals(accountService.getBalanceFor(AccountCurrency.GBP), new BigDecimal("0.00"));
        assertEquals(accountService.getBalanceFor(AccountCurrency.USD), new BigDecimal("19.65"));

        var transactionHistory = accountService.getTransactionHistory();
        assertEquals(transactionHistory.size(), 3);

    }
}
