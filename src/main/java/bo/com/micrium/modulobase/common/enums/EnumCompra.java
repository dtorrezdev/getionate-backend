package bo.com.micrium.modulobase.common.enums;

public class EnumCompra {
    public enum EstadoSolicitud {
        BORRADOR, APROBADO, ENVIADO, CANCELADO;

        public static boolean exists(String name) {
            for (EnumCompra.EstadoSolicitud s : values())
                if (s.name().equals(name))
                    return true;
            return false;
        }
    }

    public enum TIPO {
        SOLICITUD, COMPRA;

        public static boolean exists(String name) {
            for (EnumCompra.TIPO s : values())
                if (s.name().equals(name))
                    return true;
            return false;
        }
    }
}
