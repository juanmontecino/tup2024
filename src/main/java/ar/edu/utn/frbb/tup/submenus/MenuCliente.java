package ar.edu.utn.frbb.tup.submenus;

import java.util.List;

import ar.edu.utn.frbb.tup.inputs.BaseInputProcessor;
import ar.edu.utn.frbb.tup.inputs.ClienteInputProcessor;
import ar.edu.utn.frbb.tup.utils.Banco;
import ar.edu.utn.frbb.tup.utils.Cliente;




public class MenuCliente extends BaseInputProcessor{
        boolean exit = false;


        public void renderMenuCliente(Banco banco) {
            ClienteInputProcessor clienteInputProcessor = new ClienteInputProcessor();

            while (!exit) {
                System.out.println("---------------------------------------------");
                System.out.println("Bienvenido a la sección Clientes!");
                System.out.println("1. Crear un nuevo Cliente");
                System.out.println("2. Modificar un Cliente");
                System.out.println("3. Eliminar un Cliente");
                System.out.println("4. Mostrar clientes");
                System.out.println("5. Retroceder");
                System.out.print("Ingrese su opción (1-4): ");
        
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
        
                switch (choice) {
                    case 1:
                        Cliente añadir = clienteInputProcessor.ingresarCliente(banco);
                        if (añadir!= null) {
                            System.out.println("Cliente añadido correctamente.");
                        }
                        break;
                    case 2:
                        Cliente modificar = clienteInputProcessor.modificarCliente(banco);
                        if (modificar!= null) {
                            System.out.println("Cliente modificado correctamente.");
                        }
                        break;
                    case 3:
                        Cliente eliminar = clienteInputProcessor.EliminarCliente(banco);
                        if (eliminar != null){
                            System.out.println("Cliente eliminado correctamente.");
                        }
                        break;
                    case 4:
                        List<Cliente> clientes = clienteInputProcessor.mostrarClientes(banco);
                            if (clientes != null) {
                                for (Cliente c : clientes) {
                                    System.out.println(c.toString());
                                }
                            }
                    case 5:
                            exit = true;
                            break;
                    default:
                        System.out.println("Opción inválida. Por favor seleccione 1-4.");
                        break;
                }
            }
        }
    
}
