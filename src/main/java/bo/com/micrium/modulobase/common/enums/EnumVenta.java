package bo.com.micrium.modulobase.common.enums;

public class EnumVenta {
    public enum Estado {
        VENTA, PREVENTA, ANULADO;

        public static boolean exists(String name) {
            for (Estado s : values())
                if (s.name().equals(name))
                    return true;
            return false;
        }
    }

    public enum Rules {
        MONTO_NOT_ZERO,
        MONTO_TOTAL_EQUALS_DETALLE_TOTAL,
        STOCK_INSUFICIENTE,
        VENTA_NOT_FOUND_FOR_PAGOS
    }
}
