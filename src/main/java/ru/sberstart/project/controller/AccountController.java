package ru.sberstart.project.controller;

import ru.sberstart.project.entity.Account;
import ru.sberstart.project.service.AccountService;

import java.io.IOException;

public class AccountController {

    private final AccountService accountService = new AccountService();
    private final NumsGenerators generator = new NumsGenerators();

    public void depositCashToAccount(String accountNumber, double cash) throws IOException {
        Account account = new Account();
        double balance = accountService.getBalance(accountNumber);

        if (accountNumber != null && accountNumber.matches("\\d{20}")) {
            account.setAccountBalance(cash + balance);
            account.setAccountNumber(accountNumber);
        } else {
            throw new IOException("Пожалуйста, проверьте номер счет!");
        }
        accountService.update(account);
    }

    public Account checkAccountBalance(String accountNumber) throws IOException {
        Account account = new Account();

        if (accountNumber != null && accountNumber.matches("\\d{20}")) {
            account.setAccountNumber(accountNumber);
            account.setAccountBalance(accountService.getBalance(accountNumber));
        } else {
             throw new IOException("Пожалуйста, проверьте номер счетa!");
        }
        return account;
    }

    public Account getAccountByAccountNumber (String accountNumber) throws IOException {

        if (accountNumber != null && accountNumber.matches("\\d{20}")) {
            return accountService.getAccountByNumber(accountNumber);
        } else {
            throw new IOException("Пожалуйста, проверьте номер счета!");
        }
    }

    public Account createNewAccount(long id, double balance) throws IOException {
        Account newAccount = new Account();

        if (id != 0) {
            newAccount.setId(id);
            newAccount.setAccountNumber(generator.accountNumberGenerator());
            newAccount.setAccountBalance(balance);
        } else {
            throw new IOException("Пожалуйста, проверьте номер счета!");
        }

        accountService.save(newAccount);
        return newAccount;
    }
}
