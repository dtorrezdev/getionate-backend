package bo.com.micrium.modulobase.common.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // VALIDACIÓN DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        System.out.println("error: " + errors);

        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                400,
                "VALIDATION_ERROR",
                errors
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    // REGLA NEGOCIO
    /*
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex) {

        ApiError apiError = new ApiError(
                400,
                "BUSINESS_ERROR",
                ex.getMessage()
        );

        return ResponseEntity.badRequest().body(apiError);
    }*/

    // SISTEMA (DB)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDB(DataIntegrityViolationException ex) {

        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                500,
                "DATABASE_ERROR",
                ex.getMessage()
        );

        return ResponseEntity.status(500).body(apiError);
    }

    // 🔥 GENERAL
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex) {

        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                500,
                "INTERNAL_ERROR",
                ex.getMessage()
        );

        return ResponseEntity.status(500).body(apiError);
    }

}
