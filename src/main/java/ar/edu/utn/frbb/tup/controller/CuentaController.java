package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.CuentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cuenta")
@Tag(name = "Cuenta", description = "API para gestionar cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaValidator cuentaValidator;

    @PostMapping
    @Operation(summary = "Crear una nueva cuenta", description = "Crea una nueva cuenta con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta creada exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cuenta.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos de cuenta inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "La cuenta ya existe o el tipo de cuenta ya existe para el cliente",
                    content = @Content)
    })
    public Cuenta crearCuenta(
            @Parameter(description = "Datos de la cuenta a crear", required = true) @RequestBody CuentaDto cuentaDto
    ) throws TipoCuentaNoSoportadaException, TipoMonedaNoSoportadaException, TipoCuentaAlreadyExistsException, ClienteNotFoundException, CuentaAlreadyExistsException, CampoVacioException {
        cuentaValidator.validate(cuentaDto);
        return cuentaService.darDeAltaCuenta(cuentaDto);
    }

    @GetMapping("/{numeroCuenta}")
    @Operation(summary = "Buscar cuenta por número", description = "Busca y devuelve una cuenta por su número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cuenta.class)) }),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
                    content = @Content)
    })
    public Cuenta buscarCuenta(
            @Parameter(description = "Número de la cuenta a buscar", required = true) @PathVariable long numeroCuenta
    ) throws CuentaNotFoundException {
        return cuentaService.find(numeroCuenta);
    }
}