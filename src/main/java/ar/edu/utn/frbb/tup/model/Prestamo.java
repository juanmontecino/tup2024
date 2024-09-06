package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.PrestamoDto;

public class Prestamo {
    private long numeroCliente;
    private int plazoMeses;
    private long montoPrestamo;
    private TipoMoneda moneda;

    public Prestamo() {}

    public Prestamo(long numeroCliente, int plazoMeses, long montoPrestamo, TipoMoneda moneda) {
        this.numeroCliente = numeroCliente;
        this.plazoMeses = plazoMeses;
        this.montoPrestamo = montoPrestamo;
        this.moneda = moneda;
    }

    public Prestamo(PrestamoDto prestamoDto) {
        this(prestamoDto.getNumeroCliente(), prestamoDto.getPlazoMeses(), prestamoDto.getMontoPrestamo(), TipoMoneda.fromString(prestamoDto.getMoneda()));
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    public long getMontoPrestamo() {
        return montoPrestamo;
    }

    public void setMontoPrestamo(long montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }
}
