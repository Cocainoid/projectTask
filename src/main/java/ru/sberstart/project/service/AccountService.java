package ru.sberstart.project.service;

import ru.sberstart.project.bl.Util;
import ru.sberstart.project.dao.DAO;
import ru.sberstart.project.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountService extends Util implements DAO<Account> {

    private static final String SQL_INSERT = "INSERT INTO ACCOUNT (ID, ACCOUNT_NUMBER, ACCOUNT_BALANCE) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT ID, ACCOUNT_NUMBER, ACCOUNT_BALANCE FROM ACCOUNT WHERE ID = ?";
    private static final String SQL_SELECT_BALANCE_BY_ACCOUNT_NUMBER = "SELECT ACCOUNT_BALANCE FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
    private static final String SQL_SELECT_ACCOUNT_BY_NUMBER = "SELECT ID, ACCOUNT_NUMBER, ACCOUNT_BALANCE FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
    private static final String SQL_SELECT_ALL = "SELECT ID, ACCOUNT_NUMBER, ACCOUNT_BALANCE FROM ACCOUNT";
    private static final String SQL_UPDATE = "UPDATE ACCOUNT SET ACCOUNT_BALANCE = ? WHERE ACCOUNT_NUMBER = ?";
    private static final String SQL_DELETE = "DELETE FROM ACCOUNT WHERE ID = ?";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ACCOUNT (" +
            "ID BIGINT NOT NULL, " +
            "ACCOUNT_NUMBER VARCHAR(20) NOT NULL, " +
            "ACCOUNT_BALANCE FLOAT NOT NULL, " +
            "CONSTRAINT ACCOUNT_PKEY PRIMARY KEY (ACCOUNT_NUMBER));";
    private static final String SQL_DROP_TABLE = "DROP TABLE ACCOUNT;";

    @Override
    public void save(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {

            preparedStatement.setLong(1, account.getId());
            preparedStatement.setString(2, account.getAccountNumber());
            preparedStatement.setDouble(3, account.getAccountBalance());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Ошибка добавления аккаунта");
        }
    }

    public double getBalance(String accountNumber) {
        double balance = 0.0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BALANCE_BY_ACCOUNT_NUMBER)) {
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            balance = resultSet.getDouble("ACCOUNT_BALANCE");

            preparedStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            System.out.println("Ошибка проверки баланса аккаунта");
        }
        return balance;
    }

    @Override
    public Optional<Account> get(long id) {

        Account account = new Account();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            account.setId(resultSet.getLong("ID"));
            account.setAccountNumber(resultSet.getString("ACCOUNT_NUMBER"));
            account.setAccountBalance(resultSet.getDouble("ACCOUNT_BALANCE"));

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.of(account);
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getLong("ID"));
                account.setAccountNumber(resultSet.getString("ACCOUNT_NUMBER"));
                account.setAccountBalance(resultSet.getDouble("ACCOUNT_BALANCE"));

                accounts.add(account);
            }
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
        return accounts;
    }

    @Override
    public void update(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setDouble(1, account.getAccountBalance());
            preparedStatement.setString(2, account.getAccountNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            System.out.println("Ошибка внесения средств");
        }
    }

    public void updateBalance(String accountNumber, double cash) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setDouble(1,  getBalance(accountNumber)+cash);
            preparedStatement.setString(2, accountNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
    }

    @Override
    public void delete(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, account.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            System.out.println("Ошибка удаления аккаунта");
        }
    }

    public void createTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_TABLE)) {

            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DROP_TABLE)) {

            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Account getAccountByNumber(String accountNumber) {
        Account account = new Account();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NUMBER)) {
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            account.setId(resultSet.getLong("ID"));
            account.setAccountNumber(resultSet.getString("ACCOUNT_NUMBER"));
            account.setAccountBalance(resultSet.getDouble("ACCOUNT_BALANCE"));

            preparedStatement.execute();
            return null;
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            System.out.println("Аккаунт не найден");
        }

        return account;
    }
}
