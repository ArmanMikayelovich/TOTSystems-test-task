package ru.totsystems.moexiss.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.totsystems.moexiss.util.exception.*;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({DataIntegrityViolationException.class,
            HistoryNotFoundException.class,
            SecurityNotFoundException.class})
    public String handleConflict(Exception ex) {
        return ex.getMessage();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IncorrectArgumentException.class,
            NameValidationException.class,
            SecurityAlreadyExists.class})
    public String handleConflict(RuntimeException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServerSideException.class,
            SQLException.class,
            Throwable.class})
    public String handleConflict(Throwable ex) {
        return ex.getMessage();
    }

}
