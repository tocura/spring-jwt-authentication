package br.com.jwtautenthication.exceptions.handler;

import br.com.jwtautenthication.dtos.ExceptionDTO;
import br.com.jwtautenthication.dtos.FieldValidationDTO;
import br.com.jwtautenthication.exceptions.InvalidCredentialsException;
import br.com.jwtautenthication.exceptions.InvalidTokenException;
import br.com.jwtautenthication.exceptions.ResourceNotFoundException;
import br.com.jwtautenthication.exceptions.UniqueViolationException;
import br.com.jwtautenthication.utils.exceptions.FieldErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDTO> resourceNotFoundHandler(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionDTO> invalidCredentialsHandler(InvalidCredentialsException e) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDTO> invalidTokenHandler(InvalidTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ExceptionDTO> uniqueViolationHandler(UniqueViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValidHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();
        FieldValidationDTO exceptions = new FieldValidationDTO(FieldErrorUtils.processFieldErrors(fieldErrors));

        return ResponseEntity.badRequest().body(exceptions);
    }
}
