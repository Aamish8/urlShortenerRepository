package com.aamish.url_shortener.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<Errors>urlNotFoundException(UrlNotFoundException ex){
    	Errors error=Errors.builder()
    			.message(ex.getMessage())
    			.status(HttpStatus.NOT_FOUND.value())
    			.occurredAt(LocalDateTime.now())
    			.build();
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors>methodArgumentNotValid(MethodArgumentNotValidException ex){
    	String message=ex.getBindingResult()
    			       .getFieldError()
    			       .getDefaultMessage();
    	Errors error=Errors.builder()
    			.message(message)
    			.status(HttpStatus.BAD_REQUEST.value())
    			.occurredAt(LocalDateTime.now())
    			.build();
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
}
