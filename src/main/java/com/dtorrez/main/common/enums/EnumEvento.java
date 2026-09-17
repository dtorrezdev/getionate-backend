package com.dtorrez.main.common.enums;

public class EnumEvento {

    public enum NotificacionStatus {
        PENDIENTE,
        PROCESADO,
        NOTIFICADO;
    }

    public enum Type {
        SIN_STOCK("sin stock"),
        STOCK_BAJO("con stock bajo"),
        STOCK_SIN_EXPIRACION("el stock sin definir la fecha de expiracion"),
        PROD_SIN_MIN_STOCK_DISPONIBLE("sin valor en el campo minimo stock disponible"),
        PROD_SIN_DIAS_ANTES_EXPIRACION("sin valor en el campo dias antes de expiracion"),
        PROD_EXPIRADO("con fecha expirado"),
        PROD_PROXIMO_A_EXPIRAR("proximo a expirar");

        private final String valor;

        Type(String valor) {
            this.valor = valor;
        }

        public String getValor() {
            return valor;
        }

        public static String getMessageByTypeEvent(String typeEvent) {
            if (typeEvent == null || typeEvent.trim().isEmpty()) {
                return "Nombre de evento vacío";
            }

            try {
                return Type.valueOf(typeEvent.trim().toUpperCase()).getValor();
            } catch (IllegalArgumentException e) {
                return "Tipo de evento no encontrado: " + typeEvent;
            }
        }
    }

    public enum NotificacionTipo {
        ALERTA,
        RECORDATORIO,
        RECOMENDACION;
    }
}
