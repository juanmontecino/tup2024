package ar.edu.utn.frbb.tup.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para crear o actualizar una cuenta")
public class CuentaDto {

    @Schema(description = "DNI del titular de la cuenta", example = "12345678")
    private long dniTitular;

    @Schema(description = "Tipo de cuenta (CAJA_AHORRO o CUENTA_CORRIENTE)", example = "C")
    private String tipoCuenta;

    @Schema(description = "Moneda de la cuenta (PESOS o DOLARES)", example = "P")
    private String moneda;

    public long getDniTitular() {
        return dniTitular;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setDniTitular(long dniTitular) {
        this.dniTitular = dniTitular;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}