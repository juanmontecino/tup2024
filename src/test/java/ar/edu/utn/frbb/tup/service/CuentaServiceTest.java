package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoDeCuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaServiceTest {

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteDao clienteDao;

    @Mock
    private ClienteService clienteServiceMock;

    @InjectMocks
    private ClienteService clienteService;

    @InjectMocks
    private CuentaService cuentaService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDarDeAltaCuentaSuccess() throws TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException, TipoDeCuentaNoSoportadaException {
        Cuenta cuentaValida = new Cuenta();
        cuentaValida.setNumeroCuenta(123456789);
        cuentaValida.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuentaValida.setMoneda(TipoMoneda.PESOS);

        // Configuramos los mocks para que no lancen excepciones
        when(cuentaDao.find(123456789)).thenReturn(null); // Simula que la cuenta no existe
        doNothing().when(clienteServiceMock).agregarCuenta(cuentaValida, 12345678); // Simula que el cliente no tiene una cuenta de ese tipo y moneda

        // Llamamos al método darDeAltaCuenta
        cuentaService.darDeAltaCuenta(cuentaValida, 12345678);

        // Verificamos que se hayan llamado a los métodos correspondientes
        verify(clienteServiceMock, times(1)).agregarCuenta(cuentaValida, 12345678);
        verify(cuentaDao, times(1)).save(cuentaValida);
    }

    @Test
    public void testCuentaYaExistente(){
        Cuenta cuentaExistente = new Cuenta();
        cuentaExistente.setNumeroCuenta(123456789);
        when(cuentaDao.find(123456789)).thenReturn(cuentaExistente);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(123456789);

        assertThrows(CuentaAlreadyExistsException.class, () -> cuentaService.darDeAltaCuenta(cuenta, 12345678));
    }

    @Test
    public void testCuentaNoSoportada(){
        Cuenta cuentaInvalida = new Cuenta();
        cuentaInvalida.setNumeroCuenta(123456789);
        cuentaInvalida.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuentaInvalida.setMoneda(TipoMoneda.DOLARES);

        assertThrows(TipoDeCuentaNoSoportadaException.class, () -> cuentaService.darDeAltaCuenta(cuentaInvalida, 12345678));
    }

    @Test
    public void testTipoCuentaYaExistente() throws TipoCuentaAlreadyExistsException {
        // Crear el cliente y la cuenta válida
        Cliente cliente = new Cliente();
        cliente.setDni(123456789);

        Cuenta cuentaValida = new Cuenta();
        cuentaValida.setNumeroCuenta(123456789);
        cuentaValida.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuentaValida.setMoneda(TipoMoneda.PESOS);
        cliente.addCuenta(cuentaValida);

        // Configurar el mock para devolver el cliente con la cuenta válida
        when(clienteDao.find(123456789, true)).thenReturn(cliente);

        // Intentar agregar la cuenta duplicada y verificar la excepción
        Cuenta cuentaDuplicada = new Cuenta();
        cuentaDuplicada.setNumeroCuenta(123456879);
        cuentaDuplicada.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuentaDuplicada.setMoneda(TipoMoneda.PESOS);

        assertThrows(TipoCuentaAlreadyExistsException.class, () -> clienteService.agregarCuenta(cuentaDuplicada, 123456789));
    }

}
