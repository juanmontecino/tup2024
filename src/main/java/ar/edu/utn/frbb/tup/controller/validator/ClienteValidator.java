package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.ClienteDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClienteValidator {
    public void validate(ClienteDto clienteDto) {
        if (!"PERSONA_FISICA".equals(clienteDto.getTipoPersona()) && !"PERSONA_JURIDICA".equals(clienteDto.getTipoPersona())) {
            throw new IllegalArgumentException("El tipo de persona no es correcto");
        }
        try {
            LocalDate.parse(clienteDto.getFechaNacimiento());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en el formato de fecha");
        }
    }
}
