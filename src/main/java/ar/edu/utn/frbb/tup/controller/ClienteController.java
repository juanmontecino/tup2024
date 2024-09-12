package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.TipoPersonaErroneoException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cliente")
@Tag(name = "Cliente", description = "API para gestionar clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteValidator clienteValidator;

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos de cliente inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "El cliente ya existe",
                    content = @Content)
    })
    public Cliente crearCliente(
            @Parameter(description = "Datos del cliente a crear", required = true) @RequestBody ClienteDto clienteDto
    ) throws TipoPersonaErroneoException, ClienteMenorDeEdadException, ClienteAlreadyExistsException, CampoVacioException {
        clienteValidator.validate(clienteDto);
        return clienteService.darDeAltaCliente(clienteDto);
    }

    @GetMapping("/{dni}")
    @Operation(summary = "Buscar cliente por DNI", description = "Busca y devuelve un cliente por su número de DNI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content)
    })
    public Cliente buscarClientePorDni(
            @Parameter(description = "DNI del cliente a buscar", required = true) @PathVariable long dni
    ) throws ClienteNotFoundException {
        return clienteService.buscarClientePorDni(dni);
    }
}