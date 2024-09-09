package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.*;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuentaService {
    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private ClienteService clienteService;

    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoCuentaNoSoportadaException, TipoCuentaAlreadyExistsException, ClienteNotFoundException {
        Cuenta cuenta = new Cuenta(cuentaDto);

        if (cuentaDao.find(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        if (!tipoCuentaEstaSoportada(cuenta)) {
            throw new TipoCuentaNoSoportadaException("El tipo de cuenta " + cuenta.getTipoCuenta() + " en " + cuenta.getMoneda() + " no está soportado.");
        }

        clienteService.agregarCuenta(cuenta, cuentaDto.getDniTitular());
        cuentaDao.save(cuenta);
        return cuenta;
    }

    public boolean tipoCuentaEstaSoportada(Cuenta cuenta) {
        return (cuenta.getTipoCuenta() == TipoCuenta.CUENTA_CORRIENTE && cuenta.getMoneda() == TipoMoneda.PESOS) ||
                (cuenta.getTipoCuenta() == TipoCuenta.CAJA_AHORRO && (cuenta.getMoneda() == TipoMoneda.PESOS || cuenta.getMoneda() == TipoMoneda.DOLARES));
    }

    public void actualizarCuenta(Prestamo prestamo) throws CuentaNotFoundException {
        Cuenta cuenta = findByMoneda(prestamo.getMoneda());
        cuenta.setBalance(cuenta.getBalance() + prestamo.getMontoPedido());
        cuentaDao.save(cuenta);
    }

    public Cuenta find(long id) throws  CuentaNotFoundException {
        if (cuentaDao.find(id) == null) {
            throw new CuentaNotFoundException("La cuenta no existe");
        }
        return cuentaDao.find(id);
    }

    public Cuenta findByMoneda(TipoMoneda moneda) throws CuentaNotFoundException {
        if (cuentaDao.findByMoneda(moneda) == null) {
            throw new CuentaNotFoundException("La cuenta no existe");
        }
        return cuentaDao.findByMoneda(moneda);
    }

    public void  pagarCuotaPrestamo(Prestamo prestamo) throws NoAlcanzaException, CuentaNotFoundException {
        Cuenta cuenta = findByMoneda(prestamo.getMoneda());
        if (cuenta.getBalance() < prestamo.getValorCuota()) {
            throw new NoAlcanzaException("No hay suficiente saldo en la cuenta");
        }
        cuenta.setBalance(cuenta.getBalance() - prestamo.getValorCuota());
        cuentaDao.save(cuenta);
    }
}