package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoDeCuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CuentaService {
    CuentaDao cuentaDao = new CuentaDao();

    @Autowired
    ClienteService clienteService;

    //Generar casos de test para darDeAltaCuenta
    //    1 - cuenta existente
    //    2 - cuenta no soportada
    //    3 - cliente ya tiene cuenta de ese tipo
    //    4 - cuenta creada exitosamente
    public void darDeAltaCuenta(Cuenta cuenta, long dniTitular) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, TipoDeCuentaNoSoportadaException {
        if(cuentaDao.find(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe.");
        }

        //Chequear cuentas soportadas por el banco CA$ CC$ CAU$S
         if (!tipoCuentaEstaSoportada(cuenta)){
             throw new TipoDeCuentaNoSoportadaException("El tipo de cuenta " + cuenta.getTipoCuenta() + " con moneda " + cuenta.getMoneda() + " no está soportado.");
         }

        clienteService.agregarCuenta(cuenta, dniTitular);
        cuentaDao.save(cuenta);
    }

    private boolean tipoCuentaEstaSoportada(Cuenta cuenta) {
        return (cuenta.getTipoCuenta() == TipoCuenta.CAJA_AHORRO && (cuenta.getMoneda() == TipoMoneda.PESOS || cuenta.getMoneda() == TipoMoneda.DOLARES))
                || (cuenta.getTipoCuenta() == TipoCuenta.CUENTA_CORRIENTE && cuenta.getMoneda() == TipoMoneda.PESOS);
    }


    public Cuenta find(long id) {
        return cuentaDao.find(id);
    }
}
