package ar.edu.utn.frbb.tup.presentation.input;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowInfoCuenta {

    private final CuentaService cuentaService;
    private final ClienteService clienteService;

    @Autowired
    public ShowInfoCuenta(CuentaService cuentaService, ClienteService clienteService) {
        this.cuentaService = cuentaService;
        this.clienteService = clienteService;
    }

    public void mostrarInfoCuenta(long id) {
        Cuenta cuenta = cuentaService.find(id);
        if(cuenta == null) {
            System.out.println("Cuenta no encontrada!");
            return;
        }
        Cliente cliente = clienteService.buscarClientePorDni(cuenta.getTitular());

        System.out.println("Información de la Cuenta: ");
        System.out.println("Cuenta id: " + cuenta.getNumeroCuenta());
        System.out.println("Tipo de Cuenta: " + cuenta.getTipoCuenta());
        System.out.println("Tipo de Moneda: " + cuenta.getMoneda());
        System.out.println("Balance: " + cuenta.getBalance());
        System.out.println("Titular: " + cliente.getNombre() + " " +cliente.getApellido());
        System.out.println("Fecha de Creación: " + cuenta.getFechaCreacion());
    }
}
