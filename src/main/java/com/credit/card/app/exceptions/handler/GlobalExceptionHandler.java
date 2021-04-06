package com.credit.card.app.exceptions.handler;

import com.credit.card.app.exceptions.BusinessException;
import com.credit.card.app.exceptions.InvalidInputException;
import com.credit.card.app.model.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { InvalidInputException.class })
    public ResponseEntity<?> handleInvalidInputException(InvalidInputException ex) {
        logger.error("Invalid Input Exception:: ",ex);
        ExceptionResponse exceptionResponse = buildExceptionRes(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { BusinessException.class })
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        logger.error("Business Exception: ",ex);
        ExceptionResponse exceptionResponse = buildExceptionRes(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
        return new ResponseEntity(exceptionResponse,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Method Argument Not Valid : ",ex);
        String[] errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage()).toArray(String[]::new);
        ExceptionResponse exceptionResponse = buildExceptionRes(HttpStatus.BAD_REQUEST.value(),errors);
        return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<?> handleException(Exception ex) {
        logger.error("Exception: ",ex);
        ExceptionResponse exceptionResponse = buildExceptionRes(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionResponse buildExceptionRes(int httpcode, String ...message){
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode(String.valueOf(httpcode));
        response.getErrorMessage().addAll(Arrays.asList(message));
        response.setTimestamp(LocalDateTime.now());
        return  response;
    }


}
