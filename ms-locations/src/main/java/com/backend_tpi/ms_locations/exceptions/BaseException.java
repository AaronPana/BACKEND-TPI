package com.backend_tpi.ms_locations.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * ============================================
 * MÉTODOS DISPONIBLES - BaseException
 * ============================================
 *
 * 404 NOT_FOUND:
 *   - notFound(resource, field, value)
 *   - notFound(message)
 *   - notFoundById(resource, id)
 *
 * 409 CONFLICT:
 *   - conflict(message)
 *   - alreadyExists(resource, field, value)
 *
 * 400 BAD_REQUEST:
 *   - badRequest(message)
 *   - businessError(message)
 *   - invalidParameter(paramName, reason)
 *
 * 401 UNAUTHORIZED:
 *   - unauthorized(message)
 *   - invalidCredentials()
 *   - tokenExpired()
 *
 * 403 FORBIDDEN:
 *   - forbidden(message)
 *   - insufficientPermissions(requiredRole)
 *
 * 500 INTERNAL_ERROR:
 *   - internalError(message)
 *   - internalError(message, cause)
 *
 * 503 SERVICE_UNAVAILABLE:
 *   - serviceUnavailable(message)
 *
 * AUXILIARES:
 *   - require(condition, message) → lanza 400 si false
 *   - requireNonNull(object, paramName) → lanza 400 si null
 * ============================================
 */

@Getter
public class BaseException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final String errorType;

  private BaseException(String message, HttpStatus httpStatus, String errorType) {
    super(message);
    this.httpStatus = httpStatus;
    this.errorType = errorType;
  }

  private BaseException(String message, Throwable cause, HttpStatus httpStatus, String errorType) {
    super(message, cause);
    this.httpStatus = httpStatus;
    this.errorType = errorType;
  }

  // ==========================================
  // RESOURCE NOT FOUND (404) - No encontrado
  // ==========================================

  public static BaseException notFound(String resource, String field, Object value) {
    String message = String.format("Recurso '%s' con %s: %s no encontrado", resource, field, value);
    return new BaseException(message, HttpStatus.NOT_FOUND, "NOT_FOUND");
  }

  public static BaseException notFound(String message) {
    return new BaseException(message, HttpStatus.NOT_FOUND, "NOT_FOUND");
  }

  public static BaseException notFoundById(String resource, Object id) {
    return notFound(resource, "id", id);
  }

  // ==========================================
  // CONFLICT (409) - Duplicados
  // ==========================================

  public static BaseException conflict(String message) {
    return new BaseException(message, HttpStatus.CONFLICT, "CONFLICT");
  }

  public static BaseException alreadyExists(String resource, String field, Object value) {
    String message = String.format("%s con %s '%s' ya existe", resource, field, value);
    return new BaseException(message, HttpStatus.CONFLICT, "ALREADY_EXISTS");
  }

  // ==========================================
  // BAD REQUEST (400) - Errores de negocio
  // ==========================================

  public static BaseException badRequest(String message) {
    return new BaseException(message, HttpStatus.BAD_REQUEST, "BAD_REQUEST");
  }

  public static BaseException businessError(String message) {
    return new BaseException(message, HttpStatus.BAD_REQUEST, "BUSINESS_ERROR");
  }

  public static BaseException invalidParameter(String paramName, String reason) {
    String message = String.format("Parámetro '%s' inválido: %s", paramName, reason);
    return new BaseException(message, HttpStatus.BAD_REQUEST, "INVALID_PARAMETER");
  }

  // ==========================================
  // UNAUTHORIZED (401) - No autorizado
  // ==========================================

  public static BaseException unauthorized(String message) {
    return new BaseException(message, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
  }

  public static BaseException invalidCredentials() {
    return unauthorized("Credenciales inválidas");
  }

  public static BaseException tokenExpired() {
    return unauthorized("Token expirado");
  }

  // ==========================================
  // FORBIDDEN (403) - Sin permisos
  // ==========================================

  public static BaseException forbidden(String message) {
    return new BaseException(message, HttpStatus.FORBIDDEN, "FORBIDDEN");
  }

  public static BaseException insufficientPermissions(String requiredRole) {
    String message = String.format("Se requiere rol de '%s' para esta operación", requiredRole);
    return new BaseException(message, HttpStatus.FORBIDDEN, "INSUFFICIENT_PERMISSIONS");
  }

  // ==========================================
  // INTERNAL SERVER ERROR (500) - Error interno del servicio
  // ==========================================

  public static BaseException internalError(String message) {
    return new BaseException(message, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR");
  }

  public static BaseException internalError(String message, Throwable cause) {
    return new BaseException(message, cause, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR");
  }

  // ==========================================
  // SERVICE UNAVAILABLE (503) - Servicio no disponible
  // ==========================================

  public static BaseException serviceUnavailable(String message) {
    return new BaseException(message, HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE");
  }

  // ==========================================
  // MÉTODOS AUXILIARES
  // ==========================================

  public static void require(boolean condition, String message) {
    if (!condition) {
      throw badRequest(message);
    }
  }

  public static <T> T requireNonNull(T object, String paramName) {
    if (object == null) {
      throw badRequest(paramName + " no puede ser null");
    }
    return object;
  }
}
