package ar.edu.utn.frbb.tup.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para solicitar un préstamo")
public class PrestamoDto {
    @Schema(description = "Número de cliente", example = "12345678")
    private long numeroCliente;

    @Schema(description = "Plazo del préstamo en meses", example = "12")
    private int plazoMeses;

    @Schema(description = "Monto del préstamo", example = "100000")
    private long montoPrestamo;

    @Schema(description = "Moneda del préstamo (PESOS o DOLARES)", example = "P")
    private String moneda;

    public PrestamoDto() {
    }

    public PrestamoDto(long numeroCliente, int plazoMeses, long montoPrestamo, String moneda) {
        this.numeroCliente = numeroCliente;
        this.plazoMeses = plazoMeses;
        this.montoPrestamo = montoPrestamo;
        this.moneda = moneda;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public long getMontoPrestamo() {
        return montoPrestamo;
    }

    public void setMontoPrestamo(long montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}