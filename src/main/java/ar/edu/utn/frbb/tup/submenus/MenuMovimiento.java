package ar.edu.utn.frbb.tup.submenus;

import ar.edu.utn.frbb.tup.inputs.BaseInputProcessor;
import ar.edu.utn.frbb.tup.inputs.MovimientoInputProcessor;
import ar.edu.utn.frbb.tup.utils.Banco;
import ar.edu.utn.frbb.tup.utils.Movimiento;

public class MenuMovimiento extends BaseInputProcessor{

    boolean exit = false;
    public void renderMenuMovimiento(Banco banco) {
        MovimientoInputProcessor MovimientoInputProcessor = new MovimientoInputProcessor();

        while (!exit) {
            System.out.println("---------------------------------------------");

            System.out.println("Bienvenido a la sección Transacciones!");
            System.out.println("1. Realizar una Transferencia");
            System.out.println("2. Realizar un Deposito");
            System.out.println("3. Realizar un Retiro");
            System.out.println("4. Retroceder");
            System.out.print("Ingrese su opción (1-4): ");
        
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
        
            switch (choice) {
                case 1:
                    Movimiento transferencia = MovimientoInputProcessor.realizarTransferencia(banco);
                    if (transferencia != null) {
                        System.out.println("Transferencia realizada con éxito");
                        System.out.println(transferencia.toStringTransferencia());
                    }
                    break;
                case 2:
                    Movimiento deposito = MovimientoInputProcessor.realizarDeposito(banco);
                    if (deposito != null) {
                        System.out.println("Deposito realizado con éxito");
                        System.out.println(deposito.toString());
                    }
                    break;
                case 3:
                    Movimiento retiro = MovimientoInputProcessor.realizarRetiro(banco);
                    if (retiro != null) {
                        System.out.println("Retiro realizado con éxito");
                        System.out.println(retiro.toString());
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
