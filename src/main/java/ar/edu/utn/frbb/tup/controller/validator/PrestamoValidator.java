package ar.edu.utn.frbb.tup.controller.validator;


import ar.edu.utn.frbb.tup.controller.PrestamoDto;
import org.springframework.stereotype.Component;


@Component
public class PrestamoValidator {

    public void validate(PrestamoDto prestamoDto) {
        if ((!"P".equals(prestamoDto.getMoneda()) && !"D".equals(prestamoDto.getMoneda()))) {
            throw new IllegalArgumentException("El tipo de moneda no es correcto o es nulo");
        }
    }
}
