package com.example.springboot.exception;

public class BookIdMismatchException extends RuntimeException{
    public BookIdMismatchException(Long id) {
        super("Book id not found : " + id);
    }
}
