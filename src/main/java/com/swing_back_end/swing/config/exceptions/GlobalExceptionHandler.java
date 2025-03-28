package com.swing_back_end.swing.config.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFoundException(ResourceNotFoundException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "An unexpected error occurred: " + ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    String customMessage = "The provided value is not valid. Please check the allowed values.";

    Throwable cause = ex.getCause();
    if (cause instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) cause;

      String fieldName = invalidFormatException.getPath().get(0).getFieldName();

      Class<?> targetType = invalidFormatException.getTargetType();
      if (targetType.isEnum()) {
        Object[] enumValues = targetType.getEnumConstants();
        String acceptedValues = String.join(", ", java.util.Arrays.stream(enumValues)
            .map(Object::toString)
            .toArray(String[]::new));

        customMessage = String.format(
            "The provided value for field '%s' is not valid. Valid values are: %s.",
            fieldName, acceptedValues);
      }
    }

    errorResponse.put("error", customMessage);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    String customMessage = "Data integrity error. Please check the provided data.";

    String exceptionMessage = ex.getMostSpecificCause().getMessage();

    if (exceptionMessage != null && exceptionMessage.contains("violates foreign key constraint")) {
      String referencedTable = exceptionMessage.substring(exceptionMessage.indexOf("\"") + 1,
          exceptionMessage.indexOf("\"", exceptionMessage.indexOf("\"") + 1));
      String foreignKey = exceptionMessage.substring(exceptionMessage.lastIndexOf("\"") + 1,
          exceptionMessage.lastIndexOf("\"", exceptionMessage.lastIndexOf("\"") - 1));

      customMessage = String.format("The provided value for '%s' does not exist in the table '%s'.", foreignKey,
          referencedTable);
    }

    errorResponse.put("error", customMessage);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
