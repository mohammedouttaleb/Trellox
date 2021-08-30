package com.xenophobe.trellox.controller;


import com.xenophobe.trellox.dto.ErrorResponseDto;
import com.xenophobe.trellox.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@org.springframework.web.bind.annotation.ControllerAdvice(assignableTypes = MainController.class)
public class ControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserException(
            EmailAlreadyExistsException exception)
    {
        return  ResponseEntity.status(409).body(new ErrorResponseDto(exception.getCode(), exception.getMsg()));
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserException(
            InvalidCredentialsException exception)
    {
        return  ResponseEntity.status(401).body(new ErrorResponseDto(exception.getCode(), exception.getMsg()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(
            UserNotFoundException exception)
    {
        return  ResponseEntity.status(404).body(new ErrorResponseDto(exception.getCode(), exception.getMsg()));
    }
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleBoardNotFoundException(
            BoardNotFoundException exception)
    {
        return  ResponseEntity.status(404).body(new ErrorResponseDto(exception.getCode(), exception.getMsg()));
    }
    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCardNotFoundException(
            CardNotFoundException exception)
    {
        return  ResponseEntity.status(404).body(new ErrorResponseDto(exception.getCode(), exception.getMsg()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleArgumentNotValidException(
            MethodArgumentNotValidException exception)
    {
        return  ResponseEntity.status(400).body(new ErrorResponseDto("MethodArgumentNotValidException", exception.getMessage()));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(
            ConstraintViolationException exception)
    {
        return  ResponseEntity.status(500).body(new ErrorResponseDto("ConstraintViolationException", exception.getMessage()));
    }

}

