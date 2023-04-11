package com.auggie.EmployeeManagement.errorsAndValidation;

import com.auggie.EmployeeManagement.errorsAndValidation.dto.ErrorDto;
import org.apache.el.util.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request)
    {
        List<ErrorDto> errors = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getFieldErrors();

        for(FieldError fieldError : fieldErrors){
            ErrorDto errorDto = new ErrorDto(
                    fieldError.getField(),
                    fieldError.getRejectedValue(),
                    fieldError.getDefaultMessage()
            );
            errors.add(errorDto);
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message","Validation Failed!");
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException validationException){
        List<ErrorDto> errorDtos = new ArrayList<>();
        List<FieldError> fieldErrors = validationException.getErrors().getFieldErrors();

        for(FieldError fieldError : fieldErrors){
            ErrorDto errorDto = new ErrorDto(
                    fieldError.getField(),
                    fieldError.getRejectedValue(),
                    fieldError.getDefaultMessage()
            );

            errorDtos.add(errorDto);
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message","Validation failed");
        errorResponse.put("errors",errorDtos);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
