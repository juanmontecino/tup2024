package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.TipoPersonaErroneoException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClienteValidator {
    public void validate(ClienteDto clienteDto) throws TipoPersonaErroneoException, CampoVacioException {
        validateDatosCompletos(clienteDto);
        validateFechaNacimiento(clienteDto);
        validateTipoPersona(clienteDto);
    }

    public void validateTipoPersona(ClienteDto clienteDto) throws TipoPersonaErroneoException {
        if (!"F".equals(clienteDto.getTipoPersona()) && !"J".equals(clienteDto.getTipoPersona())) {
            throw new TipoPersonaErroneoException("Error: El tipo de persona no es correcto");
        }
    }

    public void validateFechaNacimiento(ClienteDto clienteDto) {
        try {
            LocalDate.parse(clienteDto.getFechaNacimiento());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en el formato de fecha");
        }
    }

    public void validateDatosCompletos(ClienteDto clienteDto) throws CampoVacioException {
        if (clienteDto.getNombre() == null || clienteDto.getNombre().isEmpty()) throw new CampoVacioException("Error: Ingrese un nombre");
        //Apellido
        if (clienteDto.getApellido() == null || clienteDto.getApellido().isEmpty()) throw new CampoVacioException("Error: Ingrese un apellido");
        //Fecha Nacimiento
        if (clienteDto.getFechaNacimiento() == null || clienteDto.getFechaNacimiento().isEmpty()) throw new CampoVacioException("Error: Ingrese una fecha de nacimiento");
        //Banco
        if (clienteDto.getBanco() == null || clienteDto.getBanco().isEmpty()) throw new CampoVacioException("Error: Ingrese un banco");
        //Tipo persona
        if (clienteDto.getTipoPersona() == null || clienteDto.getTipoPersona().isEmpty()) throw new CampoVacioException("Error: Ingrese un tipo de persona");
    }
}
