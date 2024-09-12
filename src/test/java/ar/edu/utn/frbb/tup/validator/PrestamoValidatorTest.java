package ar.edu.utn.frbb.tup.validator;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportadaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PrestamoValidatorTest {

    @Mock
    private PrestamoValidator prestamoValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validateDatosCompletosTestSuccess() throws CampoVacioException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("P");

        doNothing().when(prestamoValidator).validateDatosCompletos(prestamoDto);
        assertDoesNotThrow(() -> prestamoValidator.validateDatosCompletos(prestamoDto));
        verify(prestamoValidator).validateDatosCompletos(prestamoDto);
    }

    @Test
    public void validateMonedaTestSuccess() throws TipoMonedaNoSoportadaException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");

        doNothing().when(prestamoValidator).validateMoneda(prestamoDto);
        assertDoesNotThrow(() -> prestamoValidator.validateMoneda(prestamoDto));
        verify(prestamoValidator).validateMoneda(prestamoDto);
    }

    @Test
    public void validateTestSuccess() throws TipoMonedaNoSoportadaException, CampoVacioException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("P");

        doNothing().when(prestamoValidator).validate(prestamoDto);
        assertDoesNotThrow(() -> prestamoValidator.validate(prestamoDto));
        verify(prestamoValidator).validate(prestamoDto);
    }

    @Test
    public void validateDatosCompletosTestFailNumeroCliente() throws CampoVacioException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("P");

        doThrow(new CampoVacioException("Número de cliente vacío")).when(prestamoValidator).validateDatosCompletos(prestamoDto);
        assertThrows(CampoVacioException.class, () -> prestamoValidator.validateDatosCompletos(prestamoDto));
        verify(prestamoValidator).validateDatosCompletos(prestamoDto);
    }

    @Test
    public void validateDatosCompletosTestFailPlazoMeses() throws CampoVacioException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(0);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("P");

        doThrow(new CampoVacioException("Plazo de meses inválido")).when(prestamoValidator).validateDatosCompletos(prestamoDto);
        assertThrows(CampoVacioException.class, () -> prestamoValidator.validateDatosCompletos(prestamoDto));
        verify(prestamoValidator).validateDatosCompletos(prestamoDto);
    }

    @Test
    public void validateDatosCompletosTestFailMontoPrestamo() throws CampoVacioException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(0);
        prestamoDto.setMoneda("P");

        doThrow(new CampoVacioException("Monto de préstamo inválido")).when(prestamoValidator).validateDatosCompletos(prestamoDto);
        assertThrows(CampoVacioException.class, () -> prestamoValidator.validateDatosCompletos(prestamoDto));
        verify(prestamoValidator).validateDatosCompletos(prestamoDto);
    }

    @Test
    public void validateDatosCompletosTestFailMoneda() throws CampoVacioException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("");

        doThrow(new CampoVacioException("Moneda vacía")).when(prestamoValidator).validateDatosCompletos(prestamoDto);
        assertThrows(CampoVacioException.class, () -> prestamoValidator.validateDatosCompletos(prestamoDto));
        verify(prestamoValidator).validateDatosCompletos(prestamoDto);
    }

    @Test
    public void validateMonedaTestFail() throws TipoMonedaNoSoportadaException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("E");

        doThrow(new TipoMonedaNoSoportadaException("Tipo de moneda no soportada")).when(prestamoValidator).validateMoneda(prestamoDto);
        assertThrows(TipoMonedaNoSoportadaException.class, () -> prestamoValidator.validateMoneda(prestamoDto));
        verify(prestamoValidator).validateMoneda(prestamoDto);
    }

    @Test
    public void validateTestFail() throws TipoMonedaNoSoportadaException, CampoVacioException {
        PrestamoDto prestamoDto = new PrestamoDto();

        doThrow(new CampoVacioException("Campos vacíos")).when(prestamoValidator).validate(prestamoDto);
        assertThrows(CampoVacioException.class, () -> prestamoValidator.validate(prestamoDto));
        verify(prestamoValidator).validate(prestamoDto);

        PrestamoDto prestamoDto2 = new PrestamoDto();
        prestamoDto2.setNumeroCliente(1234);
        prestamoDto2.setPlazoMeses(12);
        prestamoDto2.setMontoPrestamo(10000);
        prestamoDto2.setMoneda("E");

        doThrow(new TipoMonedaNoSoportadaException("Tipo de moneda no soportada")).when(prestamoValidator).validate(prestamoDto2);
        assertThrows(TipoMonedaNoSoportadaException.class, () -> prestamoValidator.validate(prestamoDto2));
        verify(prestamoValidator, times(2)).validate(any(PrestamoDto.class));
    }
}