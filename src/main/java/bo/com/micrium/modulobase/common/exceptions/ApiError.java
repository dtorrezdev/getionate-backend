package bo.com.micrium.modulobase.common.exceptions;

public record ApiError(
    String timestamp,
    Integer status,
    String errorType,
    String message,
    Object details,
    String path
){ }
