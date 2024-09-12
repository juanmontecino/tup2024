package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;

import ar.edu.utn.frbb.tup.model.PrestamoResultado;
import ar.edu.utn.frbb.tup.model.exception.CampoVacioException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.clientes.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.CuentaNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.cuentas.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.exception.prestamos.PrestamoNotFoundException;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/prestamo")
@Tag(name = "Prestamo", description = "API para gestionar préstamos")
public class PrestamoController {

    @Autowired
    private PrestamoValidator prestamoValidator;

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo préstamo", description = "Crea un nuevo préstamo con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo creado exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PrestamoResultado.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos de préstamo inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente o cuenta no encontrada",
                    content = @Content)
    })
    public PrestamoResultado crearPrestamo(
            @Parameter(description = "Datos del préstamo a crear", required = true) @RequestBody PrestamoDto prestamodto
    ) throws CuentaNotFoundException, ClienteNotFoundException, TipoMonedaNoSoportadaException, CampoVacioException {
        prestamoValidator.validate(prestamodto);
        return prestamoService.solicitarPrestamo(prestamodto);
    }

    @GetMapping("/{dni}")
    @Operation(summary = "Buscar préstamos por DNI del cliente", description = "Busca y devuelve todos los préstamos asociados a un cliente por su DNI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Prestamo.class)) }),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content)
    })
    public List<Prestamo> buscarPrestamoPorDni(
            @Parameter(description = "DNI del cliente", required = true) @PathVariable long dni
    ) throws ClienteNotFoundException {
        return prestamoService.getPrestamosByCliente(dni);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Pagar cuota de préstamo", description = "Registra el pago de una cuota para un préstamo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuota pagada exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Prestamo.class)) }),
            @ApiResponse(responseCode = "404", description = "Préstamo o cuenta no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Saldo insuficiente para pagar la cuota",
                    content = @Content)
    })
    public Prestamo pagarCuota(
            @Parameter(description = "ID del préstamo", required = true) @PathVariable long id
    ) throws NoAlcanzaException, PrestamoNotFoundException, CuentaNotFoundException {
        return prestamoService.pagarCuota(id);
    }
}