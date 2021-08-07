package br.com.jwtautenthication.exceptions;

public class InvalidTokenException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Invalid authentication token.";

    public InvalidTokenException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidTokenException(String details) {
        super(DEFAULT_MESSAGE + " " + details);
    }
}
