package br.com.jwtautenthication.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Invalid credentials.";

    public InvalidCredentialsException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCredentialsException(String details) {
        super(DEFAULT_MESSAGE + " " + details);
    }
}
