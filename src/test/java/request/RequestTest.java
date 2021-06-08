package request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sberstart.project.controller.AccountController;
import ru.sberstart.project.controller.CardController;
import ru.sberstart.project.controller.NumsGenerators;
import ru.sberstart.project.entity.Account;
import ru.sberstart.project.entity.Card;
import ru.sberstart.project.server.MyHttpServer;
import ru.sberstart.project.service.AccountService;
import ru.sberstart.project.service.CardService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static request.BaseTest.getResponse;
import static request.BaseTest.sendGetRequest;

public class RequestTest {

    private final CardController cardController = new CardController();
    private final AccountController accountController = new AccountController();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CardService cardService = new CardService();
    private final AccountService accountService = new AccountService();
    private final NumsGenerators generators = new NumsGenerators();

    @Before
    public void doBefore() {
        cardService.dropTable();
        cardService.createTable();

        accountService.dropTable();
        accountService.createTable();
    }

    @Test
    public void showCardsTest() throws IOException {
        accountService.save(new Account(1L, "40817810216341000021", 10000.0));
        cardService.save(new Card(generators.cardNumberGenerator(), "40817810216341000021"));

        MyHttpServer.startServer();

        String accountNumber = "40817810216341000021";

        URL url = new URL("http://localhost:8080/getAllCardsOfAccount?accountNumber=" + accountNumber);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        sendGetRequest(connection, url);
        String response = getResponse(connection);
        JsonNode jsonNode = objectMapper.valueToTree(cardController.getAllCardsByAccountNumber(accountNumber));
        Assert.assertEquals(jsonNode.toString(), response);

        MyHttpServer.stopServer();
    }

    @Test
    public void showBalanceTest() throws IOException {
        accountService.save(new Account(1L, "40817810216341000021", 10000.0));
        MyHttpServer.startServer();

        String accountNumber = "40817810216341000021";

        URL url = new URL("http://localhost:8080/checkAccountBalance?accountNumber=" + accountNumber);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        sendGetRequest(connection, url);

        String response = getResponse(connection);
        JsonNode jsonNode = objectMapper.valueToTree(accountController.checkAccountBalance(accountNumber).getAccountBalance());

        Assert.assertEquals(jsonNode.toString(), response);

        MyHttpServer.stopServer();
    }

    @Test
    public void createCardTest() throws IOException {
        accountService.save(new Account(1L, "40817810216341000033", 11231400.0));
        cardService.save(new Card(generators.cardNumberGenerator(), "40817810216341000033"));

        MyHttpServer.startServer();

        URL url = new URL("http://localhost:8080/createCardByAccount");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonString = "{\"accountNumber\": \"40817810216341000033\"}";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }


        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Assert.assertEquals(cardController.getAllCardsByAccountNumber("40817810216341000033").size(),
                2);

        MyHttpServer.stopServer();
    }

//    @Test
//    @Ignore
//    public void updateBalance() throws IOException {
//        MyHttpServer.startServer();
//
//        URL url = new URL("http://localhost:8080/depositCashToAccount");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setDoOutput(true);
//
//        String jsonString = "{\"accountNumber\": \"40817810216341000033\", \"accountBalance\" \"100.0\"}";
//
//        try (OutputStream os = connection.getOutputStream()) {
//            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
//            os.write(input, 0, input.length);
//        }
//
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////        String inputLine;
////        StringBuffer content = new StringBuffer();
////        while ((inputLine = in.readLine()) != null) {
////            content.append(inputLine);
////        }
////        System.out.println(content);
//
//        Assert.assertEquals(accountService.getBalance("40817810216341000033"),
//                11231500.0);
//
//        MyHttpServer.stopServer();
//    }
}
