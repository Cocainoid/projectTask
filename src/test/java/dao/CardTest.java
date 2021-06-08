package dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sberstart.project.controller.NumsGenerators;
import ru.sberstart.project.entity.Card;
import ru.sberstart.project.service.CardService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardTest {
    private final CardService cardService = new CardService();
    private static List<Card> CARDS = new ArrayList<>();
    private final NumsGenerators generators = new NumsGenerators();

    @Before
    public void doBefore() throws SQLException {
        CARDS.clear();
        cardService.dropTable();
        cardService.createTable();
    }

    @Test
    public void insertCard() {
        cardService.save(new Card(generators.cardNumberGenerator(), "40817810216341000011"));
        cardService.save(new Card(generators.cardNumberGenerator(), "40817810216341000011"));
        CARDS = cardService.getAllByAccountNumber("40817810216341000011");
        Assert.assertEquals(2, CARDS.size());
    }

    @Test
    public void getAllCards() {
        try (Connection connection = cardService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO CARD (CARD_NUMBER, ACCOUNT_NUMBER) VALUES (" +
                     generators.cardNumberGenerator() + ", 40817810216341000011), (" +
                     generators.cardNumberGenerator() + ", 40817810216341000011);") ) {

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CARDS = cardService.getAllByAccountNumber("40817810216341000011");

        Assert.assertEquals(2, CARDS.size());
    }


//    @Test
//    public void updateIP() throws SQLException {
//        for (ru.sberbank.mvc.models.IndividualPartyAccount account : ACCOUNTS) {
//            accountCrud.insertRecord(account.getId());
//        }
//        double v = 17777.94;
//        for (ru.sberbank.mvc.models.IndividualPartyAccount account : ACCOUNTS) {
//            Assert.assertEquals(0.0, account.getBalance(), 0.0);
//            accountCrud.setBalance(account.getNumber(), v);
//        }
//        for (ru.sberbank.mvc.models.IndividualPartyAccount account : ACCOUNTS) {
//            Assert.assertEquals(v, accountCrud.selectAccByNumber(
//                    account.getNumber()).getBalance(), 0.0);
//        }
//    }
//
//    @Test
//    public void deleteAccount() throws SQLException {
//        for (ru.sberbank.mvc.models.IndividualPartyAccount account : ACCOUNTS) {
//            accountCrud.insertRecord(account.getId());
//        }
//        for (ru.sberbank.mvc.models.IndividualPartyAccount account : ACCOUNTS) {
//            accountCrud.deleteAccount(account.getNumber());
//        }
//        for (ru.sberbank.mvc.models.IndividualPartyAccount account : ACCOUNTS) {
//            Assert.assertNull(accountCrud.selectAccByNumber(account.getNumber()));
//        }
//    }
}
