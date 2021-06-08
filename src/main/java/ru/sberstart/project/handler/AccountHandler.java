package ru.sberstart.project.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sberstart.project.controller.AccountController;
import ru.sberstart.project.entity.Account;

import java.io.IOException;
import java.io.OutputStream;

public class AccountHandler implements HttpHandler {
    private static final AccountController accountController = new AccountController();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange request) throws IOException {
        if ("get".equalsIgnoreCase(request.getRequestMethod())) {
            handleGetRequest(request);
        } else if ("post".equalsIgnoreCase(request.getRequestMethod())) {
            handlePostRequest(request);
        } else {
            throw new IOException("Формат команды не поддерживается!");
        }
    }

    private void handlePostRequest(HttpExchange request) throws IOException {
        Account account = objectMapper.readValue(request.getRequestBody(), Account.class);
        accountController.depositCashToAccount(account.getAccountNumber(), account.getAccountBalance());

        request.sendResponseHeaders(200, "Ok".length());
        OutputStream outputStream = request.getResponseBody();

        outputStream.write("Ok".getBytes());
        outputStream.close();
        request.close();
    }

    private void handleGetRequest(HttpExchange request) throws IOException {
        String accountNumber = request.getRequestURI().toString().split("\\?")[1].split("=")[1];

        Account account = accountController.checkAccountBalance(accountNumber);

        String balance = objectMapper.writeValueAsString(account.getAccountBalance());

        request.sendResponseHeaders(200, balance.length());
        OutputStream outputStream = request.getResponseBody();

        outputStream.write(balance.getBytes());
        outputStream.close();
    }
}
