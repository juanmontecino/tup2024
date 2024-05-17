package ar.edu.utn.frbb.tup.inputs;

import java.time.LocalDateTime;

import ar.edu.utn.frbb.tup.utils.Banco;
import ar.edu.utn.frbb.tup.utils.Cliente;
import ar.edu.utn.frbb.tup.utils.Cuenta;
import ar.edu.utn.frbb.tup.utils.IdGeneratorMovimiento;
import ar.edu.utn.frbb.tup.utils.Movimiento;
import ar.edu.utn.frbb.tup.utils.tipos.TipoMovimiento;



public class MovimientoInputProcessor extends BaseInputProcessor {

    public Movimiento realizarTransferencia(Banco banco) {

        System.out.println("Ingrese su dni: ");
        long dniOrigen = scanner.nextLong ();

        Cliente cliente = banco.existeCliente(dniOrigen);

        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return null;
        }

        System.out.println("Ingrese el id de la cuenta de origen: ");
        int idCuentaOrigen = scanner.nextInt ();
        Cuenta cuentaOrigen = cliente.existeCuenta(idCuentaOrigen);

        if (cuentaOrigen == null) {
            System.out.println("Cuenta de origen no encontrada");
            return null;
        }

        System.out.println("Ingrese el dni del cliente destino: ");
        long dniDestino = scanner.nextLong ();

        Cliente clienteDestino = banco.existeCliente(dniDestino);

        if (clienteDestino == null) {
            System.out.println("Cliente destino no encontrado");
            return null;
        }

        System.out.println("Ingrese el id de la cuenta de la cuenta destino: ");
        int idCuentaDestino = scanner.nextInt ();
        Cuenta cuentaDestino = clienteDestino.existeCuenta(idCuentaDestino);

        if (cuentaDestino == null) {
            System.out.println("Cuenta de destino no encontrada");
            return null;
        }

        if (cuentaOrigen == cuentaDestino) {
            System.out.println("No se puede transferir a la misma cuenta");
            return null;
        }

        System.out.println("Ingrese la cantidad a transferir: ");
        long cantidad = scanner.nextLong ();

        if (cantidad <= 0) {
            System.out.println("La cantidad a transferir debe ser mayor a 0");
            return null;
        }

        if (cuentaOrigen.getBalance() < cantidad) {
            System.out.println("El balance de la cuenta de origen no es suficiente");
            return null;
        }

        Movimiento movimiento = new Movimiento();
        TipoMovimiento tipoMovimiento = TipoMovimiento.TRANSFERENCIA;

        movimiento.setIdMovimiento(IdGeneratorMovimiento.obtenerSiguienteIdMovimiento());
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setIdCuentaDestino(idCuentaDestino);
        movimiento.setIdCuentaOrigen(idCuentaOrigen);
        movimiento.setMonto(cantidad);
        movimiento.setTipo(tipoMovimiento);
        cuentaOrigen.setBalance(cuentaOrigen.getBalance() - cantidad);
        cuentaDestino.setBalance(cuentaDestino.getBalance() + cantidad);

        cuentaOrigen.getMovimientos().add(movimiento);
        cuentaDestino.getMovimientos().add(movimiento);
        return movimiento;
    }
    
    public Movimiento realizarDeposito(Banco banco) {

        System.out.println("Ingrese su dni: ");
        long dni = scanner.nextLong ();

        Cliente cliente = banco.existeCliente(dni);

        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return null;
        }

        System.out.println("Ingrese el id de su cuenta a la que desea depositar: ");
        int idCuenta = scanner.nextInt ();
        Cuenta cuenta = cliente.existeCuenta(idCuenta);

        if (cuenta == null) {
            System.out.println("Cuenta de origen no encontrada");
            return null;
        }

        System.out.println("Ingrese la cantidad a depositar: ");
        long cantidad = scanner.nextLong ();

        if (cantidad <= 0) {
            System.out.println("La cantidad a depositar debe ser mayor a 0");
            return null;
        }

        Movimiento movimiento = new Movimiento();
        TipoMovimiento tipoMovimiento = TipoMovimiento.DEPOSITO;

        movimiento.setIdMovimiento(IdGeneratorMovimiento.obtenerSiguienteIdMovimiento());
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipo(tipoMovimiento);
        movimiento.setIdCuentaOrigen(idCuenta);
        movimiento.setMonto(cantidad);
        cuenta.setBalance(cuenta.getBalance() + cantidad);
        cuenta.getMovimientos().add(movimiento);

        return movimiento;
    }


    public Movimiento realizarRetiro(Banco banco) {

        System.out.println("Ingrese su dni: ");
        long dni = scanner.nextLong ();

        Cliente cliente = banco.existeCliente(dni);

        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return null;
        }

        System.out.println("Ingrese el id de su cuenta de la que desea retirar: ");
        int idCuenta = scanner.nextInt ();
        Cuenta cuenta = cliente.existeCuenta(idCuenta);

        if (cuenta == null) {
            System.out.println("Cuenta de origen no encontrada");
            return null;
        }

        System.out.println("Ingrese la cantidad a retirar: ");
        long cantidad = scanner.nextLong ();

        if (cantidad <= 0) {
            System.out.println("La cantidad a retirar debe ser mayor a 0");
            return null;
        }

        if (cuenta.getBalance() < cantidad) {
            System.out.println("El balance de la cuenta de origen no es suficiente");
            return null;
        }

        Movimiento movimiento = new Movimiento();
        TipoMovimiento tipoMovimiento = TipoMovimiento.RETIRO;

        movimiento.setIdMovimiento(IdGeneratorMovimiento.obtenerSiguienteIdMovimiento());
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setIdCuentaOrigen(idCuenta);
        movimiento.setMonto(cantidad);
        movimiento.setTipo(tipoMovimiento);
        cuenta.setBalance(cuenta.getBalance() - cantidad);
        cuenta.getMovimientos().add(movimiento);

        return movimiento;
    }

}
