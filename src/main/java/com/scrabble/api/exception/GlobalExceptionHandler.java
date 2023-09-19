package com.scrabble.api.exception;

import com.scrabble.api.dto.ResponseDto;
import com.scrabble.api.enums.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorMessage = ex.getFieldError() == null
                ? "Bad request." : ex.getFieldError().getDefaultMessage();

        return getResponseEntityForBadRequest(errorMessage);
    }

    @ExceptionHandler(UserInputValidationException.class)
    public ResponseEntity<Object> handleUserInputValidationException(UserInputValidationException e) {
        return getResponseEntityForBadRequest(e.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException e) {
        ResponseDto responseDto = ResponseDto.builder()
                .status(ResponseStatus.ERROR)
                .message(e.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(responseDto);
    }

    private ResponseEntity<Object> getResponseEntityForBadRequest(String message) {
        ResponseDto responseDto = ResponseDto.builder()
                .status(ResponseStatus.ERROR)
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(responseDto);
    }

}
