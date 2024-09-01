package ar.edu.utn.frbb.tup.model;

public enum TipoPersona {

    PERSONA_FISICA("FISICA"),
    PERSONA_JURIDICA("JURIDICA");

    private final String descripcion;

    TipoPersona(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoPersona fromString(String text) {
        for (TipoPersona tipo : TipoPersona.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un TipoPersona con la descripción: " + text);
    }
}
