package ar.edu.utn.frbb.tup.utils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private int idCuenta;
    private LocalDate fechaCreacion;
    private long balance;
    private List<Movimiento> movimientos = new ArrayList<>();

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Cuenta setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public long getBalance() {
        return balance;
    }

    public Cuenta setBalance(long balance) {
        this.balance = balance;
        return this;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    
    @Override
    public String toString() {
        return "Cuenta [idCuenta=" + idCuenta + ", fechaCreacion=" + fechaCreacion + ", balance=" + balance + "]";
    }

}
