package bo.com.micrium.modulobase.common.exceptions;

import java.time.LocalDateTime;

public record ApiError(

    String timestamp,
    Integer status,
    String errorType,
    Object message
){ }
