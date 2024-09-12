package ar.edu.utn.frbb.tup.validator;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.TipoCuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportadaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CuentaValidatorTest {

    @Mock
    private CuentaValidator cuentaValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validateDatosCompletosTestSuccess() throws CampoVacioException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("P");
        cuentaDto.setDniTitular(12345678L);

        doNothing().when(cuentaValidator).validateDatosCompletos(cuentaDto);
        assertDoesNotThrow(() -> cuentaValidator.validateDatosCompletos(cuentaDto));
        verify(cuentaValidator).validateDatosCompletos(cuentaDto);
    }

    @Test
    public void validateTipoCuentaTestSuccess() throws TipoCuentaNoSoportadaException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("C");

        doNothing().when(cuentaValidator).validateTipoCuenta(cuentaDto);
        assertDoesNotThrow(() -> cuentaValidator.validateTipoCuenta(cuentaDto));
        verify(cuentaValidator).validateTipoCuenta(cuentaDto);
    }

    @Test
    public void validateMonedaTestSuccess() throws TipoMonedaNoSoportadaException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setMoneda("P");

        doNothing().when(cuentaValidator).validateMoneda(cuentaDto);
        assertDoesNotThrow(() -> cuentaValidator.validateMoneda(cuentaDto));
        verify(cuentaValidator).validateMoneda(cuentaDto);
    }

    @Test
    public void validateTestSuccess() throws TipoCuentaNoSoportadaException, TipoMonedaNoSoportadaException, CampoVacioException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("P");
        cuentaDto.setDniTitular(12345678L);

        doNothing().when(cuentaValidator).validate(cuentaDto);
        assertDoesNotThrow(() -> cuentaValidator.validate(cuentaDto));
        verify(cuentaValidator).validate(cuentaDto);
    }

    @Test
    public void validateDatosCompletosTestFail() throws CampoVacioException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("P");

        doThrow(new CampoVacioException("Campo vacío")).when(cuentaValidator).validateDatosCompletos(cuentaDto);
        assertThrows(CampoVacioException.class, () -> cuentaValidator.validateDatosCompletos(cuentaDto));
        verify(cuentaValidator).validateDatosCompletos(cuentaDto);
    }

    @Test
    public void validateTipoCuentaTestFail() throws TipoCuentaNoSoportadaException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("X");

        doThrow(new TipoCuentaNoSoportadaException("Tipo de cuenta no soportada")).when(cuentaValidator).validateTipoCuenta(cuentaDto);
        assertThrows(TipoCuentaNoSoportadaException.class, () -> cuentaValidator.validateTipoCuenta(cuentaDto));
        verify(cuentaValidator).validateTipoCuenta(cuentaDto);
    }

    @Test
    public void validateMonedaTestFail() throws TipoMonedaNoSoportadaException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setMoneda("E");

        doThrow(new TipoMonedaNoSoportadaException("Tipo de moneda no soportada")).when(cuentaValidator).validateMoneda(cuentaDto);
        assertThrows(TipoMonedaNoSoportadaException.class, () -> cuentaValidator.validateMoneda(cuentaDto));
        verify(cuentaValidator).validateMoneda(cuentaDto);
    }

    @Test
    public void validateTestFail() throws TipoCuentaNoSoportadaException, TipoMonedaNoSoportadaException, CampoVacioException {
        CuentaDto cuentaDto = new CuentaDto();

        doThrow(new CampoVacioException("Campo vacío")).when(cuentaValidator).validate(cuentaDto);
        assertThrows(CampoVacioException.class, () -> cuentaValidator.validate(cuentaDto));
        verify(cuentaValidator).validate(cuentaDto);

        CuentaDto cuentaDto2 = new CuentaDto();
        cuentaDto2.setTipoCuenta("X");
        cuentaDto2.setMoneda("E");
        cuentaDto2.setDniTitular(12345678L);

        doThrow(new TipoCuentaNoSoportadaException("Tipo de cuenta no soportada")).when(cuentaValidator).validate(cuentaDto2);
        assertThrows(TipoCuentaNoSoportadaException.class, () -> cuentaValidator.validate(cuentaDto2));
        verify(cuentaValidator, times(2)).validate(any(CuentaDto.class));
    }
}