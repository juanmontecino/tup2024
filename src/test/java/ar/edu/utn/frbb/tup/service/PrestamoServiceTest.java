package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.enums.EstadoPrestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.CuentaNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.exception.prestamos.PrestamoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrestamoServiceTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private PrestamoDao prestamoDao;

    @Mock
    private ScoreCreditService scoreCreditService;

    @InjectMocks
    private PrestamoService prestamoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void solicitarPrestamo_Success() throws CuentaNotFoundException, ClienteNotFoundException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(12345678L);
        prestamoDto.setMontoPrestamo(1000L);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMoneda("P");

        when(scoreCreditService.verifyScore(12345678L)).thenReturn(true);
        doNothing().when(clienteService).agregarPrestamo(any(Prestamo.class), anyLong());
        doNothing().when(cuentaService).actualizarCuenta(any(Prestamo.class));

        PrestamoResultado result = prestamoService.solicitarPrestamo(prestamoDto);

        assertNotNull(result);
        assertEquals(EstadoPrestamo.APROBADO, result.getEstado());
        assertEquals("El monto del préstamo fue acreditado en su cuenta", result.getMensaje());
        assertNotNull(result.getPlanPago());
        assertEquals(1, result.getPlanPago().getCuotaNro());
        assertEquals(87.5, result.getPlanPago().getCuotaMonto(), 0.01);

        verify(prestamoDao).save(any(Prestamo.class));
    }

    @Test
    void solicitarPrestamo_CreditoNoApto() throws CuentaNotFoundException, ClienteNotFoundException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(12345678L);
        prestamoDto.setMontoPrestamo(1000L);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMoneda("P");

        when(scoreCreditService.verifyScore(12345678L)).thenReturn(false);

        PrestamoResultado result = prestamoService.solicitarPrestamo(prestamoDto);

        assertNotNull(result);
        assertEquals(EstadoPrestamo.RECHAZADO, result.getEstado());
        assertEquals("El cliente no tiene un credito apto para solicitar un prestamo", result.getMensaje());

        verify(prestamoDao, never()).save(any(Prestamo.class));
    }

    @Test
    void getPrestamosByCliente_Success() throws ClienteNotFoundException {
        long dni = 12345678L;
        List<Prestamo> prestamos = Arrays.asList(
                new Prestamo(dni, 12, 1000L, TipoMoneda.PESOS),
                new Prestamo(dni, 24, 2000L, TipoMoneda.DOLARES)
        );

        when(clienteService.buscarClientePorDni(dni)).thenReturn(null); // Simulamos que el cliente existe
        when(prestamoDao.getPrestamosByCliente(dni)).thenReturn(prestamos);

        List<Prestamo> result = prestamoService.getPrestamosByCliente(dni);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dni, result.get(0).getNumeroCliente());
        assertEquals(dni, result.get(1).getNumeroCliente());
    }

    @Test
    void getPrestamosByCliente_ClienteNotFound() throws ClienteNotFoundException {
        long dni = 12345678L;

        when(clienteService.buscarClientePorDni(dni)).thenThrow(new ClienteNotFoundException("El cliente no existe"));

        assertThrows(ClienteNotFoundException.class, () -> prestamoService.getPrestamosByCliente(dni));
    }

    @Test
    void pagarCuota_Success() throws PrestamoNotFoundException, NoAlcanzaException, CuentaNotFoundException {
        long id = 1L;
        Prestamo prestamo = new Prestamo(12345678L, 12, 1000L, TipoMoneda.PESOS);
        prestamo.setId(id);

        when(prestamoDao.find(id)).thenReturn(prestamo);
        doNothing().when(cuentaService).pagarCuotaPrestamo(prestamo);

        Prestamo result = prestamoService.pagarCuota(id);

        assertNotNull(result);
        assertEquals(1, result.getCuotasPagas());
        assertEquals(963, result.getSaldoRestante());
        verify(prestamoDao).save(prestamo);
    }

    @Test
    void pagarCuota_PrestamoNotFound() {
        long id = 1L;

        when(prestamoDao.find(id)).thenReturn(null);

        assertThrows(PrestamoNotFoundException.class, () -> prestamoService.pagarCuota(id));
    }

    @Test
    void pagarCuota_NoAlcanza() throws CuentaNotFoundException, NoAlcanzaException {
        long id = 1L;
        Prestamo prestamo = new Prestamo(12345678L, 12, 1000L, TipoMoneda.PESOS);
        prestamo.setId(id);

        when(prestamoDao.find(id)).thenReturn(prestamo);
        doThrow(new NoAlcanzaException("No hay suficiente saldo en la cuenta")).when(cuentaService).pagarCuotaPrestamo(prestamo);

        assertThrows(NoAlcanzaException.class, () -> prestamoService.pagarCuota(id));
    }
}