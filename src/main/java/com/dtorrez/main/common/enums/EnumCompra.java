package com.dtorrez.main.common.enums;

public class EnumCompra {
    public enum EstadoSolicitud {
        BORRADOR, APROBADO, // Solicitud
        PENDIENTE, ENVIADO, CANCELADO; // Orden

        public static boolean exists(String name) {
            for (EnumCompra.EstadoSolicitud s : values())
                if (s.name().equals(name))
                    return true;
            return false;
        }
    }

    public enum TIPO {
        SOLICITUD, COMPRA, REPOSICION;

        public static boolean exists(String name) {
            for (EnumCompra.TIPO s : values())
                if (s.name().equals(name))
                    return true;
            return false;
        }
    }
}
