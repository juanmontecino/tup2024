package ar.edu.utn.frbb.tup.validator;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.TipoCuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportadaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CuentaValidatorTest {

    @Test
    public void validateDatosCompletosTestSuccess() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("P");
        cuentaDto.setDniTitular(12345678L);
        assertDoesNotThrow(() -> new CuentaValidator().validateDatosCompletos(cuentaDto));
    }

    @Test
    public void validateTipoCuentaTestSuccess() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("C");
        assertDoesNotThrow(() -> new CuentaValidator().validateTipoCuenta(cuentaDto));
    }

    @Test
    public void validateMonedaTestSuccess() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setMoneda("P");
        assertDoesNotThrow(() -> new CuentaValidator().validateMoneda(cuentaDto));
    }

    @Test
    public void validateTestSuccess() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("P");
        cuentaDto.setDniTitular(12345678L);
        assertDoesNotThrow(() -> new CuentaValidator().validate(cuentaDto));
    }

    @Test
    public void validateDatosCompletosTestFail() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("P");
        assertThrows(CampoVacioException.class, () -> new CuentaValidator().validateDatosCompletos(cuentaDto));
    }

    @Test
    public void validateTipoCuentaTestFail() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("X");
        assertThrows(TipoCuentaNoSoportadaException.class, () -> new CuentaValidator().validateTipoCuenta(cuentaDto));
    }

    @Test
    public void validateMonedaTestFail() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setMoneda("E");
        assertThrows(TipoMonedaNoSoportadaException.class, () -> new CuentaValidator().validateMoneda(cuentaDto));
    }

    @Test
    public void validateTestFail() {
        CuentaDto cuentaDto = new CuentaDto();
        assertThrows(CampoVacioException.class, () -> new CuentaValidator().validate(cuentaDto));

        cuentaDto.setTipoCuenta("X");
        cuentaDto.setMoneda("E");
        cuentaDto.setDniTitular(12345678L);
        assertThrows(TipoCuentaNoSoportadaException.class, () -> new CuentaValidator().validate(cuentaDto));
    }
}