package com.backend_tpi.ms_camiones.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(
      BaseException ex,
      HttpServletRequest request) {

    String logMessage = String.format("[%s] %s",
        ex.getErrorType(),
        ex.getMessage());

    if (ex.getHttpStatus().is5xxServerError()) {
      log.error("Error del servidor en {}: {}",
          request.getRequestURI(),
          logMessage);
    } else {
      log.warn("Error del cliente en {}: {}",
          request.getRequestURI(),
          logMessage);
    }

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getErrorType())
        .message(ex.getMessage())
        .path(request.getRequestURI())
        .build();

    return ResponseEntity
        .status(ex.getHttpStatus())
        .body(errorResponse);
  }

  // ==========================================
  // ERRORES DE VALIDACIÓN (@Valid)
  // ==========================================

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(
      MethodArgumentNotValidException ex,
      HttpServletRequest request) {

    Map<String, String> errores = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errores.put(error.getField(), error.getDefaultMessage())
    );

    log.warn("Error de validación en {}: {}", request.getRequestURI(), errores);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(400)
        .error("VALIDATION_ERROR")
        .message("Error en la validación de los datos")
        .path(request.getRequestURI())
        .details(errores)
        .build();

    return ResponseEntity.badRequest().body(errorResponse);
  }

  // ==========================================
  // TIPO DE PARÁMETRO INCORRECTO
  // ==========================================

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(
      MethodArgumentTypeMismatchException ex,
      HttpServletRequest request) {

    String message = String.format("El parámetro '%s' debe ser de tipo %s",
        ex.getName(),
        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido");

    log.warn("Error de tipo de parámetro en {}: {}", request.getRequestURI(), message);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .error("INVALID_PARAMETER_TYPE")
        .message(message)
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.badRequest().body(errorResponse);
  }

  // ==========================================
  // RUTA NO ENCONTRADA (404)
  // ==========================================

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
      NoHandlerFoundException ex,
      HttpServletRequest request) {

    String logMessage = String.format("Método %s no mapeado",
        ex.getHttpMethod());

    log.warn("Ruta no encontrada en {}: {}", request.getRequestURI(), logMessage);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.NOT_FOUND.value())
        .error("NOT_FOUND")
        .message("La ruta solicitada no existe")
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  // ==========================================
  // ERROR GENÉRICO (Fallback)
  // ==========================================

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(
      Exception ex,
      HttpServletRequest request) {

    log.error("Error inesperado en {}: {}",
        request.getRequestURI(),
        ex.getMessage(),
        ex);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(LocalDateTime.now())
        .status(500)
        .error("INTERNAL_ERROR")
        .message("Ocurrio un error inesperado")
        .path(request.getRequestURI())
        .build();

    return ResponseEntity.internalServerError().body(errorResponse);
  }
}
