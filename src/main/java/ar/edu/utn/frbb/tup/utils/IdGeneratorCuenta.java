package ar.edu.utn.frbb.tup.utils;

public class IdGeneratorCuenta {
    private static int ultimoIdCuenta = 0;

    public static int obtenerSiguienteIdCuenta() {
        return ++ultimoIdCuenta;
    }
}
