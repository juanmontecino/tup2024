package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.exception.TipoPersonaErroneoException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClienteValidator {
    public void validate(ClienteDto clienteDto) throws TipoPersonaErroneoException {
        validateDatosCompletos(clienteDto);
        if (!"F".equals(clienteDto.getTipoPersona()) && !"J".equals(clienteDto.getTipoPersona())) {
            throw new TipoPersonaErroneoException("El tipo de persona no es correcto");
        }
        try {
            LocalDate.parse(clienteDto.getFechaNacimiento());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en el formato de fecha");
        }
    }

    private void validateDatosCompletos(ClienteDto clienteDto) {
        //Valido que el usuario ingreso todos los datos para poder crear nuestro cliente
        //Nombre
        if (clienteDto.getNombre() == null || clienteDto.getNombre().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un nombre");
        //Apellido
        if (clienteDto.getApellido() == null || clienteDto.getApellido().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un apellido");
        //Fecha Nacimiento
        if (clienteDto.getFechaNacimiento() == null || clienteDto.getFechaNacimiento().isEmpty()) throw new IllegalArgumentException("Error: Ingrese una fecha de nacimiento");
        //Banco
        if (clienteDto.getBanco() == null || clienteDto.getBanco().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un banco");
        //Tipo persona
        if (clienteDto.getTipoPersona() == null || clienteDto.getTipoPersona().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un tipo de persona");

    }
}
