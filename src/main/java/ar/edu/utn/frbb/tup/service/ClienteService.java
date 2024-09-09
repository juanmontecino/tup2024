package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.CuentaNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    ClienteDao clienteDao;

    public ClienteService(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public Cliente darDeAltaCliente(ClienteDto clienteDto) throws Exception {
        Cliente cliente = new Cliente(clienteDto);

        if (clienteDao.find(cliente.getDni(), false) != null) {
            throw new ClienteAlreadyExistsException(cliente.getDni());
        }

        if (cliente.getEdad() < 18) {
            throw new ClienteMenorDeEdadException("El cliente debe ser mayor a 18 años");
        }

        clienteDao.save(cliente);
        return cliente;
    }

    public void agregarCuenta(Cuenta cuenta, long dniTitular) throws Exception {
        Cliente titular = buscarClientePorDni(dniTitular);
        cuenta.setTitular(titular.getDni());
        if (titular.tieneCuenta(cuenta.getTipoCuenta(), cuenta.getMoneda())) {
            throw new TipoCuentaAlreadyExistsException("El cliente ya posee una cuenta de ese tipo y moneda");
        }
        if (titular.tieneCuentaMoneda(cuenta.getMoneda())) {
            throw new TipoCuentaAlreadyExistsException("El cliente ya posee una cuenta de esa moneda");
        }
        titular.addCuenta(cuenta);
        clienteDao.save(titular);
    }

    public void agregarPrestamo (Prestamo prestamo, long dniTitular) throws Exception {
        Cliente titular = buscarClientePorDni(dniTitular);
        prestamo.setNumeroCliente(titular.getDni());
        if (!titular.tieneCuentaMoneda(prestamo.getMoneda())) {
            throw new CuentaNotFoundException("El cliente no posee una cuenta de esa moneda");
        }
        titular.addPrestamo(prestamo);
        clienteDao.save(titular);
    }

    public Cliente buscarClientePorDni(long dni) throws Exception {
        Cliente cliente = clienteDao.find(dni, true);
        if(cliente == null) {
            throw new ClienteNotFoundException("El cliente no existe");
        }
        return cliente;
    }
}
