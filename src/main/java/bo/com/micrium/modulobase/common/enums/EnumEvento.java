package bo.com.micrium.modulobase.common.enums;

public class EnumEvento {

    public enum NotificacionStatus {
        PENDIENTE,
        PROCESADO;
    }

    public enum Type {
        SIN_STOCK,
        STOCK_BAJO
    }

    public enum NotificacionTipo {
        ALERTA,
        RECORDATORIO,
        RECOMENDACION;
    }
}
