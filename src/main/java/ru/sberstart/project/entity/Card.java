package ru.sberstart.project.entity;

import java.util.Objects;

public class Card {
    private Long cardNumber;
    private String accountNumber;

    public Card() {}

    public Card(long cardNumber, String accountNumber) {
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardNumber, card.cardNumber) && Objects.equals(accountNumber, card.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, accountNumber);
    }

    @Override
    public String toString() {
        return "Card{ cardNumber=" + cardNumber +
                ", accountNumber=" + accountNumber +
                "}";
    }
}
