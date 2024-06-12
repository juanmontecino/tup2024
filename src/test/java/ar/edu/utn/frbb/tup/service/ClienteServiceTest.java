package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
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
public class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testClienteMenor18Anos() {
        Cliente clienteMenorDeEdad = new Cliente();
        clienteMenorDeEdad.setFechaNacimiento(LocalDate.of(2020, 2, 7));

        // Verifica que se arroje una IllegalArgumentException al intentar dar de alta un cliente menor de 18 años
        assertThrows(IllegalArgumentException.class, () -> clienteService.darDeAltaCliente(clienteMenorDeEdad));
    }

    @Test
    public void testClienteSuccess() throws ClienteAlreadyExistsException {//caso feliz
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.of(1978,3,25));
        cliente.setDni(29857643);
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);

        // Llama al método darDeAltaCliente del servicio ClienteService
        clienteService.darDeAltaCliente(cliente);

        // Verifica que el método save del clienteDao se haya llamado exactamente una vez con el cliente creado
        verify(clienteDao, times(1)).save(cliente);
    }

    @Test
    public void testClienteAlreadyExistsException() throws ClienteAlreadyExistsException {//caso triste 1
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456437);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3,25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        // Configura el mock clienteDao para que devuelva un cliente existente cuando se llame al método find con el DNI 26456437
        when(clienteDao.find(26456437, false)).thenReturn(new Cliente());

        // Verifica que se arroje una ClienteAlreadyExistsException al intentar dar de alta un cliente con un DNI que ya existe
        assertThrows(ClienteAlreadyExistsException.class, () -> clienteService.darDeAltaCliente(pepeRino));
    }

    @Test
    public void testAgregarCuentaAClienteSuccess() throws TipoCuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3,25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Configura el mock clienteDao para que devuelva el objeto Cliente cuando se llame al método find con el DNI 26456439
        when(clienteDao.find(26456439, true)).thenReturn(pepeRino);

        // Llama al método agregarCuenta del servicio ClienteService
        clienteService.agregarCuenta(cuenta, pepeRino.getDni());

        // Verifica que el método save del clienteDao se haya llamado exactamente una vez con el objeto Cliente actualizado
        verify(clienteDao, times(1)).save(pepeRino);

        // Verifica que el cliente tenga una cuenta y que el titular de la cuenta sea el cliente
        assertEquals(1, pepeRino.getCuentas().size());
        assertEquals(pepeRino, cuenta.getTitular());
    }

    @Test
    public void testAgregarCuentaAClienteDuplicadaDolares() throws TipoCuentaAlreadyExistsException {
        Cliente luciano = new Cliente();
        luciano.setDni(26456439);
        luciano.setNombre("Pepe");
        luciano.setApellido("Rino");
        luciano.setFechaNacimiento(LocalDate.of(1978, 3,25));
        luciano.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta()
                .setMoneda(TipoMoneda.DOLARES)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Configura el mock clienteDao para que devuelva el objeto Cliente cuando se llame al método find con el DNI 26456439
        when(clienteDao.find(26456439, true)).thenReturn(luciano);

        // Llama al método agregarCuenta del servicio ClienteService
        clienteService.agregarCuenta(cuenta, luciano.getDni());

        // Crea una nueva cuenta del mismo tipo y moneda
        Cuenta cuenta2 = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Verifica que se arroje una TipoCuentaAlreadyExistsException al intentar agregar una cuenta del mismo tipo y moneda
        assertThrows(TipoCuentaAlreadyExistsException.class, () -> clienteService.agregarCuenta(cuenta2, luciano.getDni()));
        // Verifica que el método save del clienteDao se haya llamado exactamente una vez con el objeto Cliente actualizado
        verify(clienteDao, times(1)).save(luciano);
        // Verifica que el cliente tenga una cuenta y que el titular de la cuenta sea el cliente
        assertEquals(1, luciano.getCuentas().size());
        assertEquals(luciano, cuenta.getTitular());
    }
    @Test
    public void testAgregarCuentaAClienteDuplicada() throws TipoCuentaAlreadyExistsException {
        Cliente luciano = new Cliente();
        luciano.setDni(26456439);
        luciano.setNombre("Pepe");
        luciano.setApellido("Rino");
        luciano.setFechaNacimiento(LocalDate.of(1978, 3,25));
        luciano.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Configura el mock clienteDao para que devuelva el objeto Cliente cuando se llame al método find con el DNI 26456439
        when(clienteDao.find(26456439, true)).thenReturn(luciano);

        // Llama al método agregarCuenta del servicio ClienteService
        clienteService.agregarCuenta(cuenta, luciano.getDni());

        // Crea una nueva cuenta del mismo tipo y moneda
        Cuenta cuenta2 = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Verifica que se arroje una TipoCuentaAlreadyExistsException al intentar agregar una cuenta del mismo tipo y moneda
        assertThrows(TipoCuentaAlreadyExistsException.class, () -> clienteService.agregarCuenta(cuenta2, luciano.getDni()));
        // Verifica que el método save del clienteDao se haya llamado exactamente una vez con el objeto Cliente actualizado
        verify(clienteDao, times(1)).save(luciano);
        // Verifica que el cliente tenga una cuenta y que el titular de la cuenta sea el cliente
        assertEquals(1, luciano.getCuentas().size());
        assertEquals(luciano, cuenta.getTitular());
    }

    //Agregar una CA$ y CC$ --> success 2 cuentas, titular peperino...

    @Test
    public void testAgregarCajaAhorroYCuentaCorrienteSuccess() throws TipoCuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3,25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuentaAhorro = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Configura el mock clienteDao para que devuelva el objeto Cliente cuando se llame al método find con el DNI 26456439
        when(clienteDao.find(26456439, true)).thenReturn(pepeRino);

        // Llama al método agregarCuenta del servicio ClienteService
        clienteService.agregarCuenta(cuentaAhorro, pepeRino.getDni());

        Cuenta cuentaCorriente = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);

        // Llama al método agregarCuenta del servicio ClienteService
        clienteService.agregarCuenta(cuentaCorriente, pepeRino.getDni());

        // Verifica que el método save del clienteDao se haya llamado exactamente dos veces con el objeto Cliente actualizado
        verify(clienteDao, times(2)).save(pepeRino);

        // Verifica que el cliente tenga una cuenta y que el titular de la cuenta sea el cliente
        assertEquals(2, pepeRino.getCuentas().size());
        assertEquals(pepeRino, cuentaAhorro.getTitular());
        assertEquals(pepeRino, cuentaCorriente.getTitular());

    }


    //Agregar una CA$ y CAU$S --> success 2 cuentas, titular peperino...
    @Test
    public void testAgregarCuentaPesosYDolaresAClienteSuccess() throws TipoCuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3,25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuentaPesos = new Cuenta()
                .setMoneda(TipoMoneda.PESOS)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Configura el mock clienteDao para que devuelva el objeto Cliente cuando se llame al método find con el DNI 26456439
        when(clienteDao.find(26456439, true)).thenReturn(pepeRino);

        // Llama al método agregarCuenta del servicio ClienteService
        clienteService.agregarCuenta(cuentaPesos, pepeRino.getDni());

        Cuenta cuentaDolares = new Cuenta()
                .setMoneda(TipoMoneda.DOLARES)
                .setBalance(500000)
                .setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Llama al método agregarCuenta del servicio ClienteService
        clienteService.agregarCuenta(cuentaDolares, pepeRino.getDni());

        // Verifica que el método save del clienteDao se haya llamado exactamente dos veces con el objeto Cliente actualizado
        verify(clienteDao, times(2)).save(pepeRino);

        // Verifica que el cliente tenga una cuenta y que el titular de la cuenta sea el cliente
        assertEquals(2, pepeRino.getCuentas().size());
        assertEquals(pepeRino, cuentaPesos.getTitular());
        assertEquals(pepeRino, cuentaDolares.getTitular());

    }

    @Test
    public void testBuscarPorDniSuccess(){
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3,25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        // Configura el mock clienteDao para que devuelva el objeto Cliente cuando se llame al método find con el DNI 26456439
        when(clienteDao.find(26456439, true)).thenReturn(pepeRino);

        // Llama al método buscarClientePorDni del servicio ClienteService
        Cliente ClienteEncontrado = clienteService.buscarClientePorDni(26456439);

        assertEquals(pepeRino, ClienteEncontrado);
    }

    @Test
    public void testBuscarPorDniFail() {
        // Configura el mock clienteDao para que devuelva null cuando se llame al método find con el DNI 87654321
        when(clienteDao.find(87654321, true)).thenReturn(null);

        // Verifica que se arroje una IllegalArgumentException cuando se busca un cliente que no existe
        assertThrows(IllegalArgumentException.class, () -> clienteService.buscarClientePorDni(87654321));
    }
}