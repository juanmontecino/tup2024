package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void darDeAltaCliente_Success() throws ClienteAlreadyExistsException, ClienteMenorDeEdadException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("John");
        clienteDto.setApellido("Doe");
        clienteDto.setFechaNacimiento("1990-01-01");
        clienteDto.setTipoPersona("F");
        clienteDto.setBanco("Banco Test");

        when(clienteDao.find(12345678L, false)).thenReturn(null);

        Cliente result = clienteService.darDeAltaCliente(clienteDto);

        assertNotNull(result);
        assertEquals(12345678L, result.getDni());
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals(LocalDate.of(1990, 1, 1), result.getFechaNacimiento());

        verify(clienteDao).save(any(Cliente.class));
    }

    @Test
    void darDeAltaCliente_ClienteAlreadyExists() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("Juan");
        clienteDto.setApellido("Pérez");
        clienteDto.setFechaNacimiento(LocalDate.now().minusYears(20).toString());
        clienteDto.setTipoPersona("F");
        clienteDto.setBanco("BancoTest");

        Cliente clienteExistente = new Cliente(clienteDto);

        when(clienteDao.find(12345678L, false)).thenReturn(clienteExistente);

        assertThrows(ClienteAlreadyExistsException.class, () -> clienteService.darDeAltaCliente(clienteDto));
    }

    @Test
    void darDeAltaCliente_ClienteMenorDeEdad() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("Juan");
        clienteDto.setApellido("Pérez");
        clienteDto.setFechaNacimiento(LocalDate.now().minusYears(17).format(DateTimeFormatter.ISO_LOCAL_DATE));
        clienteDto.setTipoPersona("F");
        clienteDto.setBanco("BancoTest");

        when(clienteDao.find(12345678L, false)).thenReturn(null);

        assertThrows(ClienteMenorDeEdadException.class, () -> clienteService.darDeAltaCliente(clienteDto));
    }

    @Test
    void agregarCuenta_Success() throws TipoCuentaAlreadyExistsException, ClienteNotFoundException {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);

        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuenta.setMoneda(TipoMoneda.PESOS);

        when(clienteDao.find(12345678L, true)).thenReturn(cliente);

        clienteService.agregarCuenta(cuenta, 12345678L);

        assertTrue(cliente.getCuentas().contains(cuenta));
        verify(clienteDao).save(cliente);
    }

    @Test
    void buscarClientePorDni_Success() throws ClienteNotFoundException {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);

        when(clienteDao.find(12345678L, true)).thenReturn(cliente);

        Cliente result = clienteService.buscarClientePorDni(12345678L);

        assertNotNull(result);
        assertEquals(12345678L, result.getDni());
    }

    @Test
    void buscarClientePorDni_ClienteNotFound() {
        when(clienteDao.find(12345678L, true)).thenReturn(null);

        assertThrows(ClienteNotFoundException.class, () -> clienteService.buscarClientePorDni(12345678L));
    }
}