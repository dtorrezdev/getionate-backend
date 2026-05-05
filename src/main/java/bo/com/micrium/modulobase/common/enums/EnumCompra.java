package bo.com.micrium.modulobase.common.enums;

public class EnumCompra {
    public enum Estado {
        SOLICITUD, COMPRA, ANULADO;

        public static boolean exists(String name) {
            for (EnumCompra.Estado s : values())
                if (s.name().equals(name))
                    return true;
            return false;
        }
    }
}
