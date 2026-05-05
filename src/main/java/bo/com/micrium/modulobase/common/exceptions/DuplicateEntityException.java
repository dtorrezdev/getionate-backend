package bo.com.micrium.modulobase.common.exceptions;

import lombok.Getter;

@Getter
public class DuplicateEntityException extends RuntimeException {
    private final String entityName;
    private final String criteria;
    private final Object value;
    private final String code = "DE-409";
    public final static String NAME = "DUPLICATE_ENTITY_ERROR";

    public DuplicateEntityException(String entityName, String criteria, Object value) {
        super(buildMessage(entityName));
        this.entityName = entityName;
        this.criteria = criteria;
        this.value = value;
    }

    private static String buildMessage(String entityName) {
        return String.format("%s duplicado.", entityName);
    }

    public String getDetails() {
        return String.format("Ya existe %s con %s: %s", entityName, criteria, value);
    }
}
