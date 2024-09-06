package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoService {

    @Autowired ClienteService clienteService;

    @Autowired CuentaService cuentaService;

    public Prestamo solicitarPrestamo (PrestamoDto prestamoDto) {
        Prestamo prestamo = new Prestamo(prestamoDto);
        clienteService.agregarPrestamo(prestamo, prestamo.getNumeroCliente());
        cuentaService.actualizarCuenta(prestamo);

        return prestamo;
    }
}
