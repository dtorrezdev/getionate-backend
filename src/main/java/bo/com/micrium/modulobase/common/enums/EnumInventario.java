package bo.com.micrium.modulobase.common.enums;

public class EnumInventario {
    public enum StockStatus {
       HAY_STOCK,
        AGOTADO,
        POCO_STOCK;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
