package my_shop.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildResponse(
            String code,
            String message,
            Map<String, List<String>> details,
            HttpStatus status,
            HttpServletRequest request
    ) {
        String originalPath = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        String finalPath = (originalPath != null) ? originalPath : request.getRequestURI();

        ErrorResponse error = new ErrorResponse(
                code,
                message,
                details,
                LocalDateTime.now(),
                finalPath
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });

        return buildResponse(
                "INVALID_ARGUMENTS",
                "Error de validación en los campos enviados",
                errors,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildResponse("RESOURCE_NOT_FOUND", ex.getMessage(), null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorResponse> handleOutOfStock(OutOfStockException ex, HttpServletRequest request) {
        return buildResponse("OUT_OF_STOCK", ex.getMessage(), null, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStock(InsufficientStockException ex, HttpServletRequest request) {
        return buildResponse("INSUFFICIENT_STOCK", ex.getMessage(), null, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse("USER_ALREADY_EXISTS", ex.getMessage(), null, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(MaxAddressesReachedException.class)
    public ResponseEntity<ErrorResponse> handleMaxAddressesReached(MaxAddressesReachedException ex, HttpServletRequest request) {
        return buildResponse("MAX_ADDRESSES_LIMIT", ex.getMessage(), null, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<ErrorResponse> handlePaymentProcessing(PaymentProcessingException ex, HttpServletRequest request) {
        return buildResponse("PAYMENT_GATEWAY_ERROR", ex.getMessage(), null, HttpStatus.BAD_GATEWAY, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationError(
            AuthenticationException ex,
            HttpServletRequest request
    ) {
        String code = "UNAUTHORIZED";
        String message = ex.getMessage();

        if (ex.getClass().getSimpleName().equals("BadCredentialsException")) {
            code = "INVALID_CREDENTIALS";
            message = "Credenciales incorrectas";
        } else if (ex.getClass().getSimpleName().equals("InsufficientAuthenticationException")) {
            code = "TOKEN_MISSING";
            message = "No se proporcionó un token o es inválido";
        }

        return buildResponse(
                code,
                message,
                null,
                HttpStatus.UNAUTHORIZED,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
            Exception ex,
            HttpServletRequest request
    ) {
        ex.printStackTrace();
        return buildResponse(
                "INTERNAL_SERVER_ERROR",
                "Ocurrió un error inesperado en el servidor",
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServer(
            InternalServerException ex,
            HttpServletRequest request) {

        ex.printStackTrace();

        return buildResponse(
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }
}
