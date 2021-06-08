package ru.sberstart.project.controller;

import ru.sberstart.project.entity.Card;
import ru.sberstart.project.service.CardService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardController {

    private final CardService cardService = new CardService();
    private final NumsGenerators generator = new NumsGenerators();

    public void createNewCardByAccountNumber (String accountNumber) throws IOException {
        Card newCard = new Card();


        if (accountNumber != null && accountNumber.matches("\\d{20}")) {
            newCard.setAccountNumber(accountNumber);
            newCard.setCardNumber(generator.cardNumberGenerator());
        } else {
            throw new IOException("Пожалуйста, проверьте номер счета!");
        }

        cardService.save(newCard);
    }

    public List<Card> getAllCardsByAccountNumber (String accountNumber) throws IOException {
        List<Card> cardList = new ArrayList<>();

        if (accountNumber != null && accountNumber.matches("\\d{20}")) {
            cardList.addAll(cardService.getAllByAccountNumber(accountNumber));
        } else {
            throw new IOException("Пожалуйста, проверьте номер счета!");
        }
        return cardList;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
