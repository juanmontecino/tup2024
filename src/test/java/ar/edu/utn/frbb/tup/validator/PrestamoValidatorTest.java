package ar.edu.utn.frbb.tup.validator;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportadaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrestamoValidatorTest {

    @Test
    public void validateDatosCompletosTestSuccess() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("P");
        assertDoesNotThrow(() -> new PrestamoValidator().validateDatosCompletos(prestamoDto));
    }

    @Test
    public void validateMonedaTestSuccess() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        assertDoesNotThrow(() -> new PrestamoValidator().validateMoneda(prestamoDto));
    }

    @Test
    public void validateTestSuccess() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("P");
        assertDoesNotThrow(() -> new PrestamoValidator().validate(prestamoDto));
    }

    @Test
    public void validateDatosCompletosTestFailNumeroCliente() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("P");
        assertThrows(CampoVacioException.class, () -> new PrestamoValidator().validateDatosCompletos(prestamoDto));
    }

    @Test
    public void validateDatosCompletosTestFailPlazoMeses() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(0);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("P");
        assertThrows(CampoVacioException.class, () -> new PrestamoValidator().validateDatosCompletos(prestamoDto));
    }

    @Test
    public void validateDatosCompletosTestFailMontoPrestamo() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(0);
        prestamoDto.setMoneda("P");
        assertThrows(CampoVacioException.class, () -> new PrestamoValidator().validateDatosCompletos(prestamoDto));
    }

    @Test
    public void validateDatosCompletosTestFailMoneda() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("");
        assertThrows(CampoVacioException.class, () -> new PrestamoValidator().validateDatosCompletos(prestamoDto));
    }

    @Test
    public void validateMonedaTestFail() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("E");
        assertThrows(TipoMonedaNoSoportadaException.class, () -> new PrestamoValidator().validateMoneda(prestamoDto));
    }

    @Test
    public void validateTestFail() {
        PrestamoDto prestamoDto = new PrestamoDto();
        assertThrows(CampoVacioException.class, () -> new PrestamoValidator().validate(prestamoDto));

        prestamoDto.setNumeroCliente(1234);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("E");
        assertThrows(TipoMonedaNoSoportadaException.class, () -> new PrestamoValidator().validate(prestamoDto));
    }
}