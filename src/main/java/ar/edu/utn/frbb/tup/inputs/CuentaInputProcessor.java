package ar.edu.utn.frbb.tup.inputs;
import java.time.LocalDate;
import java.util.List;

import ar.edu.utn.frbb.tup.utils.Banco;
import ar.edu.utn.frbb.tup.utils.Cliente;
import ar.edu.utn.frbb.tup.utils.Cuenta;
import ar.edu.utn.frbb.tup.utils.IdGeneratorCuenta;



public class CuentaInputProcessor extends BaseInputProcessor{
    public Cuenta ingresarCuentas(Banco banco) {
        // Ingreso de datos del Cliente
        Cuenta cuenta = new Cuenta();
        // clearScreen();

        System.out.println("Ingrese el dni del cliente: ");
        long dni = scanner.nextLong ();

        Cliente cliente = banco.existeCliente(dni);

        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return null;
        }

        cuenta.setIdCuenta(IdGeneratorCuenta.obtenerSiguienteIdCuenta());
        cuenta.setFechaCreacion(LocalDate.now());

        System.out.println("Ingrese el balance inicial: ");
        long balance = scanner.nextLong ();

        cuenta.setBalance(balance);

        cliente.getCuentas().add(cuenta);
        return cuenta;
    }

    public List <Cuenta> mostrarCuentas(Banco banco) {

        System.out.println("Ingrese el dni del cliente: ");
        long dni = scanner.nextLong ();

        Cliente cliente = banco.existeCliente(dni);

        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return null;
        }

        return cliente.getCuentas();
    }


}
