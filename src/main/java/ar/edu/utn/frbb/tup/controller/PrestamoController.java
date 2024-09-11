package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;

import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.CuentaNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.exception.prestamos.PrestamoNotFoundException;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoValidator prestamoValidator;

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public PrestamoResultado crearPrestamo(@RequestBody PrestamoDto prestamodto) throws CuentaNotFoundException, ClienteNotFoundException, TipoMonedaNoSoportadaException, CampoVacioException {
        prestamoValidator.validate(prestamodto);
        return prestamoService.solicitarPrestamo(prestamodto);
    }

    @GetMapping("/{dni}")
    public List<Prestamo> buscarPrestamoPorDni(@PathVariable long dni) throws ClienteNotFoundException {
        return prestamoService.getPrestamosByCliente(dni);
    }

    @PostMapping("/{id}")
    public Prestamo pagarCuota(@PathVariable long id) throws NoAlcanzaException, PrestamoNotFoundException, CuentaNotFoundException {
        return prestamoService.pagarCuota(id);
    }
}