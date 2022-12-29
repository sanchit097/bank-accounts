package com.test.code.accounts.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Error> handleValidationException(ValidationException exception) {
        log.error(exception.getErrorCode().description, exception);
        return new ResponseEntity<>(
                Error.builder()
                        .status(400)
                        .errorCode(exception.getErrorCode().name())
                        .description(exception.getErrorCode().description)
                        .build()
                , HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(DataNotFoundException exception) {
        log.error(exception.getErrorCode().description, exception);
        return new ResponseEntity<>(
                Error.builder()
                        .status(404)
                        .errorCode(exception.getErrorCode().name())
                        .description(exception.getErrorCode().description)
                        .build()
                , HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleException(Exception exception) {
        log.error("Unhandled Exception", exception);
        return new ResponseEntity<>(
                Error.builder()
                        .status(500)
                        .errorCode("Unhandled Exception")
                        .description(exception.getMessage())
                        .build()
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleBadRequestException(MethodArgumentNotValidException exception) {
        log.error("bad request Exception", exception);
        return new ResponseEntity<>(
                Error.builder()
                        .status(400)
                        .errorCode("bad request Exception")
                        .description(exception.getMessage())
                        .build()
                , HttpStatus.BAD_REQUEST
        );
    }
}
