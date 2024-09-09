package ar.edu.utn.frbb.tup.controller.dto;

public class CuentaDto {

    private long dniTitular;
    private String tipoCuenta;
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
