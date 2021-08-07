package br.com.jwtautenthication.exceptions;

public class UniqueViolationException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = " already in use";

    public UniqueViolationException(String details) {
        super(details + DEFAULT_MESSAGE);
    }
}
