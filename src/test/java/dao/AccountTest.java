package dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sberstart.project.controller.NumsGenerators;
import ru.sberstart.project.entity.Account;
import ru.sberstart.project.service.AccountService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountTest {
    private final AccountService accountService = new AccountService();
    private static List<Account> ACCOUNTS = new ArrayList<>();
    private final NumsGenerators generators = new NumsGenerators();

    @Before
    public void doBefore() throws SQLException {
        ACCOUNTS.clear();
        accountService.dropTable();
        accountService.createTable();
    }

    @Test
    public void getAllAccounts() {
        accountService.save(new Account(1L, "40817810216341000033", 11231400.0));
        accountService.save(new Account(1L, "40817810216341000021", 10000.0));
        accountService.save(new Account(1L, "40817810216341000051", 10901230.0));

        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1L, "40817810216341000033", 11231400.0));
        accountList.add(new Account(1L, "40817810216341000021", 10000.0));
        accountList.add(new Account(1L, "40817810216341000051", 10901230.0));

        Assert.assertEquals(accountList, accountService.getAll());

    }

    @Test
    public void checkAccountBalance() {
        Account account = new Account(1L, "40817810216341000011", 100.0);
        accountService.save(account);
        double balance = accountService.getBalance("40817810216341000011");

        Assert.assertEquals(100.0, balance, 0.0);
    }

    @Test
    public void updateAccountBalance () {
        Account account = new Account(1L, "40817810216341000011", 100.0);
        accountService.save(account);

        accountService.updateBalance("40817810216341000011", 10000.0);

        double balance = accountService.getBalance("40817810216341000011");

        Assert.assertEquals(10100.00, balance, 0.0);
    }
}
