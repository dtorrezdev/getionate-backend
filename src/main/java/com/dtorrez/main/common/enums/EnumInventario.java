package com.dtorrez.main.common.enums;

public class EnumInventario {
    public enum StockStatus {
       HAY_STOCK,
        AGOTADO,
        POCO_STOCK,
        NO_APLICA;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum StockExpiracion {
        VIGENTE,
        POR_VENCER,
        CRITICO,
        VENCIDO;
    }
}
