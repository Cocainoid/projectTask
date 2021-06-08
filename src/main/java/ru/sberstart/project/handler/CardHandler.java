package ru.sberstart.project.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sberstart.project.controller.CardController;
import ru.sberstart.project.entity.Card;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CardHandler implements HttpHandler {
    private static final CardController cardController = new CardController();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange request) throws IOException {
        if ("get".equalsIgnoreCase(request.getRequestMethod())) {
            handleGetRequest(request);
        } else
            if ("post".equalsIgnoreCase(request.getRequestMethod())) {
                handlePostRequest(request);
            } else {
                throw new IOException("Формат команды не поддерживается!");
        }
    }

    private void handlePostRequest(HttpExchange request) throws IOException {
        Card card = objectMapper.readValue(request.getRequestBody(), Card.class);
        cardController.createNewCardByAccountNumber(card.getAccountNumber());

        request.sendResponseHeaders(200, "Ok".length());
        OutputStream outputStream = request.getResponseBody();

        outputStream.write("Ok".getBytes());
        outputStream.close();
        request.close();
    }

    private void handleGetRequest(HttpExchange request) throws IOException {
        String accountNumber = request.getRequestURI().toString().split("\\?")[1].split("=")[1];

        List<Card> cardList = cardController.getAllCardsByAccountNumber(accountNumber);

        ArrayNode array = objectMapper.valueToTree(cardList);

        String cardResult = array.toString();

        request.sendResponseHeaders(200, cardResult.length());
        OutputStream outputStream = request.getResponseBody();

        outputStream.write(cardResult.getBytes());
        outputStream.close();
    }
}
