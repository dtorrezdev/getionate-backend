package bo.com.micrium.modulobase.common.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String entityName;
    private final String criteria;
    private final Object value;
    private final String code = "ENF-404";
    public final static String NAME = "ENTITY_NOT_FOUND_ERROR";

    public EntityNotFoundException(String entityName, String criteria, Object value) {
        super(buildMessage(entityName));
        this.entityName = entityName;
        this.criteria = criteria;
        this.value = value;
    }

    public EntityNotFoundException(String entityName, String criteria) {
        super(buildMessage(entityName, criteria));
        this.entityName = entityName;
        this.criteria = criteria;
        this.value = "";
    }

    private static String buildMessage(String entityName) {
        return String.format("%s no encontrado.", entityName);
    }

    private static String buildMessage(String entityName, String criteria) {
        return String.format("%s no creado para %s", entityName, criteria);
    }

    public String getDetails() {
        return String.format("No existe %s con %s: %s", entityName, criteria, value);
    }
}
