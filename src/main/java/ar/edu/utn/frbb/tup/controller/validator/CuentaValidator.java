package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.model.exception.cuentas.TipoCuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportadaException;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;


@Component
public class CuentaValidator {

    public void validate(CuentaDto cuentaDto) throws TipoCuentaNoSoportadaException, TipoMonedaNoSoportadaException {
        validateDatosCompletos(cuentaDto);
        validateTipoCuenta(cuentaDto);
        validateMoneda(cuentaDto);
    }

    private void validateTipoCuenta(CuentaDto cuentaDto) throws TipoCuentaNoSoportadaException {
        if (!"C".equals(cuentaDto.getTipoCuenta()) && !"A".equals(cuentaDto.getTipoCuenta())) {
            throw new TipoCuentaNoSoportadaException("El tipo de cuenta no es correcto");
        }
    }

    private void validateMoneda(CuentaDto cuentaDto) throws TipoMonedaNoSoportadaException {
        if ((!"P".equals(cuentaDto.getMoneda()) && !"D".equals(cuentaDto.getMoneda()))) {
            throw new TipoMonedaNoSoportadaException("El tipo de moneda no es correcto o es nulo");
        }
    }

    private void validateDatosCompletos(CuentaDto cuentaDto) {
        if (cuentaDto.getTipoCuenta() == null || cuentaDto.getTipoCuenta().isEmpty()) { throw new IllegalArgumentException("Error: Ingrese un tipo de cuenta ");}
        if (cuentaDto.getMoneda() == null || cuentaDto.getMoneda().isEmpty()) { throw new IllegalArgumentException("Error: Ingrese una moneda");}
        if (cuentaDto.getDniTitular() == 0) { throw new IllegalArgumentException("Error: Ingrese un dni");}
    }

}
