package ar.edu.utn.frbb.tup.utils;
import java.time.LocalDateTime;
import ar.edu.utn.frbb.tup.utils.tipos.TipoMovimiento;


public class Movimiento {
        private int idMovimiento;
        private LocalDateTime fecha;
        private TipoMovimiento tipo;
        private double monto;
        private int idCuentaOrigen;
        private int idCuentaDestino;

        public LocalDateTime getFecha() {
                return fecha;
        }
        public void setFecha(LocalDateTime fecha) {
                this.fecha = fecha;
        }
        public TipoMovimiento getTipo() {
                return tipo;
        }
        public void setTipo(TipoMovimiento tipo) {
                this.tipo = tipo;
        }
        public double getMonto() {
                return monto;
        }
        public void setMonto(double monto) {
                this.monto = monto;
        }

        public int getIdMovimiento() {
                return idMovimiento;
        }
        public void setIdMovimiento(int idMovimiento) {
                this.idMovimiento = idMovimiento;
        }
        public int getIdCuentaOrigen() {
                return idCuentaOrigen;
        }
        public void setIdCuentaOrigen(int idCuentaOrigen) {
                this.idCuentaOrigen = idCuentaOrigen;
        }
        public int getIdCuentaDestino() {
                return idCuentaDestino;
        }
        public void setIdCuentaDestino(int idCuentaDestino) {
                this.idCuentaDestino = idCuentaDestino;
        }
        @Override
        public String toString() {
                return "Movimiento [idMovimiento=" + idMovimiento + ", fecha=" + fecha + ", tipo=" + tipo + ", monto="
                                + monto + ", idCuentaOrigen=" + idCuentaOrigen + "]";
        }

        public String toStringTransferencia() {
                return "Movimiento [idMovimiento=" + idMovimiento + ", fecha=" + fecha + ", tipo=" + tipo + ", monto="
                                + monto + ", idCuentaOrigen=" + idCuentaOrigen + ", idCuentaDestino=" + idCuentaDestino
                                + "]";
        }

        

}
