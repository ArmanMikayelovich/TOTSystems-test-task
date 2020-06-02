package ru.totsystems.moexiss.util.exception;

public class SecurityAlreadyExists extends RuntimeException {
    public SecurityAlreadyExists(String message) {
        super ("Security with SECID : " + message + "already exists.");

    }
}
