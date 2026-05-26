package bo.com.micrium.modulobase.common.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
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
        Object target = ex.getBindingResult().getTarget();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        String entityName = target != null
                ? target.getClass().getSimpleName()
                : "Entidad";
        System.out.println("error: " + errors);

        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                HttpStatus.BAD_REQUEST.value(), // 400: Invalido Input cliente error
                "VALIDATION_ERROR",
                entityName + " tiene campos invalidos.",
                errors,
                ""
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    // Recurso no encontrado
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex) {
        int status = HttpStatus.NOT_FOUND.value();
        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                HttpStatus.NOT_FOUND.value(),  // 404 Not Found
                EntityNotFoundException.NAME,
                ex.getMessage(),
                ex.getDetails(),
                ""
        );
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ApiError> handleDuplicateEntity(DuplicateEntityException ex) {
        int status = HttpStatus.CONFLICT.value();
        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                HttpStatus.CONFLICT.value(), // 409: conflict
                DuplicateEntityException.NAME,
                ex.getMessage(),
                ex.getDetails(),
                ""
        );
        return ResponseEntity.status(status).body(apiError);
    }

    // REGLA NEGOCIO
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessRuleException ex) {
        int status = HttpStatus.PRECONDITION_FAILED.value();
        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                status, // 412: Precondition Failed (Logica negocio)
                BusinessRuleException.NAME,
                ex.getMessage(),
                ex,
                ""
        );
        return ResponseEntity.status(status).body(apiError);
    }

    // SISTEMA (DB)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDB(DataIntegrityViolationException ex) {
        int status = HttpStatus.CONFLICT.value();
        String details = ex.getMostSpecificCause().getMessage();

        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                status, // 409
                "DATABASE_ERROR",
                "Error de integridad de datos.",
                details,
                ""
        );
        return ResponseEntity.status(status).body(apiError);
    }

    /**
     * Captura errores: tabla inexistente, columna inexistente, query mal formada, JPQL inválido
     *    BadSqlGrammarException: errores de sintaxis SQL
     *    InvalidResultSetAccessException: columna invalida/ inexistente
     *    TypeMismatchDataAccessException:  incompatibilidad de tipos entre el valor que Java intenta enviar/recibir
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ApiError> handleSqlGrammar(InvalidDataAccessResourceUsageException ex) {
        int status = HttpStatus.BAD_REQUEST.value();

        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                status, // 400
                "DATABASE_ERROR",
                "Error al obtener los datos.",
                ex.getMessage(),
                ""
        );
        return ResponseEntity.status(status).body(apiError);
    }

    // GENERAL
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        ApiError apiError = new ApiError(
                LocalDateTime.now().toString(),
                status,
                "INTERNAL_ERROR",
                ex.getMessage(),
                ex.getLocalizedMessage(),
                ""
        );
        return ResponseEntity.status(status).body(apiError);
    }
}
