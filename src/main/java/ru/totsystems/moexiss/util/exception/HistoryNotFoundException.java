package ru.totsystems.moexiss.util.exception;

public class HistoryNotFoundException extends RuntimeException {
    public HistoryNotFoundException(String id) {
        super("History with id " + id + " not found.");
    }

    public HistoryNotFoundException() {
        super("History must have id.");
    }
}
