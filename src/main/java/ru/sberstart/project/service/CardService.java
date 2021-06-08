package ru.sberstart.project.service;

import ru.sberstart.project.bl.Util;
import ru.sberstart.project.dao.DAO;
import ru.sberstart.project.entity.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardService extends Util implements DAO<Card> {

    private static final String SQL_INSERT = "INSERT INTO CARD (CARD_NUMBER, ACCOUNT_NUMBER) VALUES (?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT ID, CARD_NUMBER, ACCOUNT_NUMBER FROM CARD WHERE ID = ?";
    private static final String SQL_SELECT_ALL_BY_ACCOUNT_NUMBER = "SELECT CARD_NUMBER, ACCOUNT_NUMBER FROM CARD WHERE ACCOUNT_NUMBER = ?";
    private static final String SQL_DELETE = "DELETE FROM CARD WHERE CARD_NUMBER = ?";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Card (" +
//    private static final String SQL_SELECT_BY_ACCOUNT_NUMBER = "SELECT ID, CARD_NUMBER, ACCOUNT_NUMBER FROM CARD WHERE ACCOUNT_NUMBER = ?";
//    private static final String SQL_SELECT_ALL = "SELECT ID, CARD_NUMBER, ACCOUNT_NUMBER FROM CARD";
//    private static final String SQL_UPDATE = "UPDATE ACCOUNT SET ACCOUNT_BALANCE = ? WHERE ID = ?";
            "ID BIGINT NOT NULL AUTO_INCREMENT,\n" +
            "CARD_NUMBER BIGINT NOT NULL,\n" +
            "ACCOUNT_NUMBER VARCHAR NOT NULL,\n" +
            "CONSTRAINT CARD_PKEY PRIMARY KEY (CARD_NUMBER));";
    private static final String SQL_DROP_TABLE = "DROP TABLE CARD;";

    @Override
    public void save(Card card) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {

            preparedStatement.setLong(1, card.getCardNumber());
            preparedStatement.setString(2, card.getAccountNumber());

            preparedStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public Optional<Card> get(long id) {
        Card card = new Card();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            card.setCardNumber(resultSet.getLong("CARD_NUMBER"));
            card.setAccountNumber(resultSet.getString("ACCOUNT_NUMBER"));

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.of(card);
    }

    public List<Card> getAllByAccountNumber (String accountNumber){
        List<Card> cards = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_BY_ACCOUNT_NUMBER)) {
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Card card = new Card();
                card.setCardNumber(resultSet.getLong("CARD_NUMBER"));
                card.setAccountNumber(resultSet.getString("ACCOUNT_NUMBER"));

                cards.add(card);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cards;
    }

    @Override
    public List<Card> getAll() {

        return null;
    }

    @Override
    public void update(Card card) {

    }

    @Override
    public void delete(Card card) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, card.getCardNumber());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
}
