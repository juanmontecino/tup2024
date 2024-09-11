package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.cuentas.*;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaServiceTest {

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void darDeAltaCuenta_Success() throws CuentaAlreadyExistsException, TipoCuentaNoSoportadaException, TipoCuentaAlreadyExistsException, ClienteNotFoundException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniTitular(12345678L);
        cuentaDto.setTipoCuenta("A");
        cuentaDto.setMoneda("P");

        when(cuentaDao.find(anyLong())).thenReturn(null);
        doNothing().when(clienteService).agregarCuenta(any(Cuenta.class), anyLong());

        Cuenta result = cuentaService.darDeAltaCuenta(cuentaDto);

        assertNotNull(result);
        assertEquals(TipoCuenta.CAJA_AHORRO, result.getTipoCuenta());
        assertEquals(TipoMoneda.PESOS, result.getMoneda());
        assertEquals(12345678L, result.getTitular());

        verify(cuentaDao).save(any(Cuenta.class));
    }

    @Test
    void darDeAltaCuenta_CuentaAlreadyExists() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniTitular(12345678L);
        cuentaDto.setTipoCuenta("A");
        cuentaDto.setMoneda("P");

        when(cuentaDao.find(anyLong())).thenReturn(new Cuenta());

        assertThrows(CuentaAlreadyExistsException.class, () -> cuentaService.darDeAltaCuenta(cuentaDto));
    }

    @Test
    void darDeAltaCuenta_TipoCuentaNoSoportada() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniTitular(12345678L);
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("D");

        when(cuentaDao.find(anyLong())).thenReturn(null);

        assertThrows(TipoCuentaNoSoportadaException.class, () -> cuentaService.darDeAltaCuenta(cuentaDto));
    }


    @Test
    void actualizarCuenta_Success() throws CuentaNotFoundException {
        Prestamo prestamo = new Prestamo();
        prestamo.setMontoPedido(1000L);
        prestamo.setMoneda(TipoMoneda.PESOS);

        Cuenta cuenta = new Cuenta();
        cuenta.setBalance(500L);
        cuenta.setMoneda(TipoMoneda.PESOS);

        when(cuentaDao.findByMoneda(TipoMoneda.PESOS)).thenReturn(cuenta);

        cuentaService.actualizarCuenta(prestamo);

        assertEquals(1500L, cuenta.getBalance());
        verify(cuentaDao).save(cuenta);
    }

    @Test
    void find_Success() throws CuentaNotFoundException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(123L);

        when(cuentaDao.find(123L)).thenReturn(cuenta);

        Cuenta result = cuentaService.find(123L);

        assertNotNull(result);
        assertEquals(123L, result.getNumeroCuenta());
    }

    @Test
    void find_CuentaNotFound() {
        when(cuentaDao.find(123L)).thenReturn(null);

        assertThrows(CuentaNotFoundException.class, () -> cuentaService.find(123L));
    }

    @Test
    void pagarCuotaPrestamo_Success() throws NoAlcanzaException, CuentaNotFoundException {
        Prestamo prestamo = new Prestamo();
        prestamo.setValorCuota(100L);
        prestamo.setMoneda(TipoMoneda.PESOS);

        Cuenta cuenta = new Cuenta();
        cuenta.setBalance(500L);
        cuenta.setMoneda(TipoMoneda.PESOS);

        when(cuentaDao.findByMoneda(TipoMoneda.PESOS)).thenReturn(cuenta);

        cuentaService.pagarCuotaPrestamo(prestamo);

        assertEquals(400L, cuenta.getBalance());
        verify(cuentaDao).save(cuenta);
    }

    @Test
    void pagarCuotaPrestamo_NoAlcanza() {
        Prestamo prestamo = new Prestamo();
        prestamo.setValorCuota(1000L);
        prestamo.setMoneda(TipoMoneda.PESOS);

        Cuenta cuenta = new Cuenta();
        cuenta.setBalance(500L);
        cuenta.setMoneda(TipoMoneda.PESOS);

        when(cuentaDao.findByMoneda(TipoMoneda.PESOS)).thenReturn(cuenta);

        assertThrows(NoAlcanzaException.class, () -> cuentaService.pagarCuotaPrestamo(prestamo));
    }
}