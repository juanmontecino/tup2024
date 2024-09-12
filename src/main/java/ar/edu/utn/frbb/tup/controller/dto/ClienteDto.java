package ar.edu.utn.frbb.tup.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para crear o actualizar un cliente")
public class ClienteDto extends PersonaDto {
    @Schema(description = "Tipo de persona (FISICA o JURIDICA)", example = "F")
    private String tipoPersona;

    @Schema(description = "Nombre del banco", example = "Banco de la Nación Argentina")
    private String banco;

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}