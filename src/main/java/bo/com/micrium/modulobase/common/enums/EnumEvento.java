package bo.com.micrium.modulobase.common.enums;

public class EnumEvento {

    public enum NotificacionStatus {
        PENDIENTE,
        PROCESADO;
    }

    public enum Type {
        SIN_STOCK,
        STOCK_BAJO,
        STOCK_SIN_EXPIRACION,
        PROD_SIN_MIN_STOCK_DISPONIBLE,
        PROD_SIN_DIAS_ANTES_EXPIRACION;

    }

    public enum NotificacionTipo {
        ALERTA,
        RECORDATORIO,
        RECOMENDACION;
    }
}
