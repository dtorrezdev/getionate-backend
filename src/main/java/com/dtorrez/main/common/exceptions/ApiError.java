package com.dtorrez.main.common.exceptions;

public record ApiError(
    String timestamp,
    Integer status,
    String errorType,
    String message,
    Object details,
    String path
){ }
