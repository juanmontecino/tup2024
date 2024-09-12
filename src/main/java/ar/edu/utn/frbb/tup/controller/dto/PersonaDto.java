package ar.edu.utn.frbb.tup.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PersonaDto {
    @Schema(description = "Nombre", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido", example = "Perez")
    private String apellido;

    @Schema(description = "DNI", example = "12345678")
    private long dni;

    @Schema(description = "Fecha de nacimiento", example = "1990-01-01")
    private String fechaNacimiento;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
