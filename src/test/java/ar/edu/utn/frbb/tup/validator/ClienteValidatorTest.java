package ar.edu.utn.frbb.tup.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.TipoPersonaErroneoException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.CuentaAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteValidatorTest {

    @Test
    public void validateDatosCompletosTestSuccess() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("John");
        clienteDto.setApellido("Doe");
        clienteDto.setFechaNacimiento("1990-01-01");
        clienteDto.setTipoPersona("F");
        clienteDto.setBanco("Banco Test");
        assertDoesNotThrow(() -> new ClienteValidator().validateDatosCompletos(clienteDto));
    }

    @Test
    public void validateTipoPersonaTestSuccess() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        assertDoesNotThrow(() -> new ClienteValidator().validateTipoPersona(clienteDto));
    }


    @Test
    public void validateFechaNacimientoTestSuccess() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFechaNacimiento("1990-01-01");
        assertDoesNotThrow(() -> new ClienteValidator().validateFechaNacimiento(clienteDto));
    }


    @Test
    public void validateTestSuccess() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("John");
        clienteDto.setApellido("Doe");
        clienteDto.setFechaNacimiento("1990-01-01");
        clienteDto.setTipoPersona("F");
        clienteDto.setBanco("Banco Test");
        assertDoesNotThrow(() -> new ClienteValidator().validate(clienteDto));
    }

    @Test
    public void validateDatosCompletosTestFail() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("John");
        assertThrows(CampoVacioException.class, () -> new ClienteValidator().validateDatosCompletos(clienteDto));
    }


    @Test
    public void validateTipoPersonaTestFail() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("h");
        assertThrows(TipoPersonaErroneoException.class, () -> new ClienteValidator().validateTipoPersona(clienteDto));
    }


    @Test
    public void validateFechaNacimientoTestFail() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFechaNacimiento("200-01-01");
        assertThrows(IllegalArgumentException.class, () -> new ClienteValidator().validateFechaNacimiento(clienteDto));
    }


    @Test
    public void validateTestFail() {
        ClienteDto clienteDto = new ClienteDto();
        assertThrows(CampoVacioException.class, () -> new ClienteValidator().validateDatosCompletos(clienteDto));
        assertThrows(TipoPersonaErroneoException.class, () -> new ClienteValidator().validateTipoPersona(clienteDto));
        assertThrows(IllegalArgumentException.class, () -> new ClienteValidator().validateFechaNacimiento(clienteDto));
    }

}
