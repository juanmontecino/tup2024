package ar.edu.utn.frbb.tup.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.utils.tipos.TipoPersona;

public class Cliente extends Persona{

    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;
    private List<Cuenta> cuentas = new ArrayList<>();

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }
    
    public Cuenta existeCuenta(int idComparar){
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getIdCuenta() == idComparar) {
                return cuenta;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Cliente [getNombre()=" + getNombre() + ", getApellido()=" + getApellido() + ", getDni()=" + getDni()
                + ", getCuentas()=" + getCuentas() + "]";
    }


    
}
