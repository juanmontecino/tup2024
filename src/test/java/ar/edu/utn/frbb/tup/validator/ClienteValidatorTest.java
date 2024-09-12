package ar.edu.utn.frbb.tup.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.TipoPersonaErroneoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClienteValidatorTest {

    @Mock
    private ClienteValidator clienteValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validateDatosCompletosTestSuccess() throws CampoVacioException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("John");
        clienteDto.setApellido("Doe");
        clienteDto.setFechaNacimiento("1990-01-01");
        clienteDto.setTipoPersona("F");
        clienteDto.setBanco("Banco Test");

        doNothing().when(clienteValidator).validateDatosCompletos(clienteDto);
        assertDoesNotThrow(() -> clienteValidator.validateDatosCompletos(clienteDto));
        verify(clienteValidator).validateDatosCompletos(clienteDto);
    }

    @Test
    public void validateTipoPersonaTestSuccess() throws TipoPersonaErroneoException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");

        doNothing().when(clienteValidator).validateTipoPersona(clienteDto);
        assertDoesNotThrow(() -> clienteValidator.validateTipoPersona(clienteDto));
        verify(clienteValidator).validateTipoPersona(clienteDto);
    }

    @Test
    public void validateFechaNacimientoTestSuccess() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFechaNacimiento("1990-01-01");

        doNothing().when(clienteValidator).validateFechaNacimiento(clienteDto);
        assertDoesNotThrow(() -> clienteValidator.validateFechaNacimiento(clienteDto));
        verify(clienteValidator).validateFechaNacimiento(clienteDto);
    }

    @Test
    public void validateTestSuccess() throws TipoPersonaErroneoException, CampoVacioException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("John");
        clienteDto.setApellido("Doe");
        clienteDto.setFechaNacimiento("1990-01-01");
        clienteDto.setTipoPersona("F");
        clienteDto.setBanco("Banco Test");

        doNothing().when(clienteValidator).validate(clienteDto);
        assertDoesNotThrow(() -> clienteValidator.validate(clienteDto));
        verify(clienteValidator).validate(clienteDto);
    }

    @Test
    public void validateDatosCompletosTestFail() throws CampoVacioException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("John");

        doThrow(new CampoVacioException("Campo vacío")).when(clienteValidator).validateDatosCompletos(clienteDto);
        assertThrows(CampoVacioException.class, () -> clienteValidator.validateDatosCompletos(clienteDto));
        verify(clienteValidator).validateDatosCompletos(clienteDto);
    }

    @Test
    public void validateTipoPersonaTestFail() throws TipoPersonaErroneoException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("h");

        doThrow(new TipoPersonaErroneoException("Tipo de persona erróneo")).when(clienteValidator).validateTipoPersona(clienteDto);
        assertThrows(TipoPersonaErroneoException.class, () -> clienteValidator.validateTipoPersona(clienteDto));
        verify(clienteValidator).validateTipoPersona(clienteDto);
    }

    @Test
    public void validateFechaNacimientoTestFail() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFechaNacimiento("200-01-01");

        doThrow(new IllegalArgumentException("Fecha inválida")).when(clienteValidator).validateFechaNacimiento(clienteDto);
        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateFechaNacimiento(clienteDto));
        verify(clienteValidator).validateFechaNacimiento(clienteDto);
    }

    @Test
    public void validateTestFail() throws CampoVacioException, TipoPersonaErroneoException {
        ClienteDto clienteDto = new ClienteDto();

        doThrow(new CampoVacioException("Campo vacío")).when(clienteValidator).validateDatosCompletos(clienteDto);
        doThrow(new TipoPersonaErroneoException("Tipo de persona erróneo")).when(clienteValidator).validateTipoPersona(clienteDto);
        doThrow(new IllegalArgumentException("Fecha inválida")).when(clienteValidator).validateFechaNacimiento(clienteDto);

        assertThrows(CampoVacioException.class, () -> clienteValidator.validateDatosCompletos(clienteDto));
        assertThrows(TipoPersonaErroneoException.class, () -> clienteValidator.validateTipoPersona(clienteDto));
        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateFechaNacimiento(clienteDto));

        verify(clienteValidator).validateDatosCompletos(clienteDto);
        verify(clienteValidator).validateTipoPersona(clienteDto);
        verify(clienteValidator).validateFechaNacimiento(clienteDto);
    }
}