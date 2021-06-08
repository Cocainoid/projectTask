package ru.sberstart.project.controller;

public class NumsGenerators {

    public long cardNumberGenerator() {
        return (long) (1100_0000_0000_0000L + Math.random() * 8900_0000_0000_0000L);
    }

    public String accountNumberGenerator() {
        long nextNum = (long) (1000_0000_0000L + Math.random() * 9000_0000_0000L);
        return "40817802" + nextNum;
    }
}
