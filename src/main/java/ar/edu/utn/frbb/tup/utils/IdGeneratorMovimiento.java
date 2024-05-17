package ar.edu.utn.frbb.tup.utils;
public class IdGeneratorMovimiento {
    private static int ultimoIdMovimiento = 0;
    
    public static int obtenerSiguienteIdMovimiento() {
        return ++ultimoIdMovimiento;
            
    }
}
    

