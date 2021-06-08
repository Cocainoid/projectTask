package ru.sberstart.project.server;

import ru.sberstart.project.handler.AccountHandler;
import ru.sberstart.project.handler.CardHandler;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class MyHttpServer {

    private static HttpServer server;
    private static final Logger logger = Logger.getLogger("myLogger");

    public static void main(String[] args) throws IOException {
        startServer();

    }

    public static void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        CardHandler cardHandler = new CardHandler();
        AccountHandler accountHandler = new AccountHandler();

        server.createContext("/createCardByAccount", cardHandler);
        server.createContext("/getAllCardsOfAccount", cardHandler);

        server.createContext("/depositCashToAccount", accountHandler);
        server.createContext("/checkAccountBalance", accountHandler);

        server.start();
        logger.info("Server started on port 8080");
    }

    public static void stopServer() throws IOException {
        server.stop(0);
    }

}
