package bo.com.micrium.modulobase.common.enums;

public class EnumCompra {
    public enum Estado {
        ABIERTA, PENDIENTE, APROBADO, RECHAZADO;

        public static boolean exists(String name) {
            for (EnumCompra.Estado s : values())
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
