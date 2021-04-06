package com.credit.card.app.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvalidInputException extends RuntimeException {
    private String message;

    public InvalidInputException(String message) {
        super(message);
        this.message = message;
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
