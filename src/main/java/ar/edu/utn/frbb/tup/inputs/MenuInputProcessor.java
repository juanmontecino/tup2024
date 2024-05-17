package ar.edu.utn.frbb.tup.inputs;

import ar.edu.utn.frbb.tup.submenus.MenuCliente;
import ar.edu.utn.frbb.tup.submenus.MenuCuenta;
import ar.edu.utn.frbb.tup.submenus.MenuMovimiento;

import ar.edu.utn.frbb.tup.utils.Banco;

public class MenuInputProcessor extends BaseInputProcessor{
    boolean exit = false;

    public void renderMenu(Banco banco) {
        while (!exit) {
            System.out.println("---------------------------------------------");

            System.out.println("Bienvenido a la aplicación de Banco!");
            System.out.println("1. Sección Clientes");
            System.out.println("2. Sección Cuentas");
            System.out.println("3. Sección Transacciones");
            System.out.println("4. Salir");
            System.out.print("Ingrese su opción (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume la nueva línea después de leer la opción

            switch (choice) {
                case 1:
                    MenuCliente menuCliente = new MenuCliente();
                    menuCliente.renderMenuCliente(banco);
                    break;
                case 2:
                    MenuCuenta menuCuenta = new MenuCuenta();
                    menuCuenta.renderMenuCuenta(banco);
                    break;
                case 3:
                    MenuMovimiento menuMovimiento = new MenuMovimiento();
                    menuMovimiento.renderMenuMovimiento(banco);
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
