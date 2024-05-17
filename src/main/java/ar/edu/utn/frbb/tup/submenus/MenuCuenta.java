package ar.edu.utn.frbb.tup.submenus;
import java.util.List;

import ar.edu.utn.frbb.tup.inputs.BaseInputProcessor;
import ar.edu.utn.frbb.tup.inputs.CuentaInputProcessor;
import ar.edu.utn.frbb.tup.utils.Banco;
import ar.edu.utn.frbb.tup.utils.Cuenta;

public class MenuCuenta extends BaseInputProcessor{
        boolean exit = false;


        public void renderMenuCuenta(Banco banco) {
            CuentaInputProcessor CuentaInputProcessor = new CuentaInputProcessor();

            while (!exit) {
                System.out.println("---------------------------------------------");

                System.out.println("Bienvenido a la sección Cuentas!");
                System.out.println("1. Crear una nueva Cuenta");
                System.out.println("2. Mostrar las Cuentas");
                System.out.println("3. Eliminar un Cuenta");
                System.out.println("4. Retroceder");
                System.out.print("Ingrese su opción (1-4): ");
        
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
        
                switch (choice) {
                    case 1:
                        Cuenta cuenta = CuentaInputProcessor.ingresarCuentas(banco);
                        if (cuenta != null) {
                            System.out.println("Cuenta creada con éxito");
                            System.out.println(cuenta.toString());
                        }
                        break;
                    case 2:
                        List<Cuenta> cuentas = CuentaInputProcessor.mostrarCuentas(banco);
                        if (cuentas != null) {
                            for (Cuenta c : cuentas) {
                                System.out.println(c);
                            }
                        }
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor seleccione 1-4.");
                        break;
                }
            }
        }
    
}
