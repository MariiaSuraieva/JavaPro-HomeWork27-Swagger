package com.example.demowithtests.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<?> handleDeleteException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was deleted"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //Сохранение юзера - 1
    @ExceptionHandler(CopyDataException.class)
    public ResponseEntity<?> copyDataException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("The user with this email already exist"), HttpStatus.BAD_REQUEST);
    }

    //Сохранение юзера - 2
    @ExceptionHandler(EmailAbsentException.class)
    public ResponseEntity<?> emailAbsentException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("You schould wright email for user"), HttpStatus.BAD_REQUEST);
    }

    //Получение списка юзеров - 1
    //Удаление списка юзеров - 1
    @ExceptionHandler(ListHasNoAnyElementsException.class)
    public ResponseEntity<?> listHasNoAnyElementsException() {
       return new ResponseEntity<>(new MyGlobalExceptionHandler("This database is empty yet"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ListWasDeletedException.class)
    public ResponseEntity<?> listWasDeletedException() {
       return new ResponseEntity<>(new MyGlobalExceptionHandler("This database was deleted"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdIsNotExistException.class)
    public ResponseEntity<?> idIsNotExistException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user is not exist"), HttpStatus.BAD_REQUEST);
    }

    //Получение юзера по id - 2
    @ExceptionHandler(WrongDataException.class)
    public ResponseEntity<?> wrongDataInputException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("You wrote wrong data, it should be a number"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsNotExistException.class)
    public ResponseEntity<?> userIsNotExistException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("The user is not exist"), HttpStatus.BAD_REQUEST);
    }

    //удаление юзера по айди - 2
    @ExceptionHandler(UserAlreadyDeletedException.class)
    public ResponseEntity<?> userAlreadyDeletedException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was already deleted"), HttpStatus.BAD_REQUEST);
    }


    @Data
            // @AllArgsConstructor
    private class MyGlobalExceptionHandler {
        private String message;

        public MyGlobalExceptionHandler(String message) {
            this.message = message;
        }
    }





}
