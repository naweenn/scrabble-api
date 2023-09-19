package com.scrabble.api.exception;

import com.scrabble.api.dto.ResponseDto;
import com.scrabble.api.enums.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorMessage = ex.getFieldError() == null
                ? "Bad request." : ex.getFieldError().getDefaultMessage();

        ResponseDto responseDto = ResponseDto.builder()
                .status(ResponseStatus.ERROR)
                .message(errorMessage)
                .build();
        return ResponseEntity.badRequest().body(responseDto);
    }

}
