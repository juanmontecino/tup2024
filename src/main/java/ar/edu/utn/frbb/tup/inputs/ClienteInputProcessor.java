package ar.edu.utn.frbb.tup.inputs;

import java.time.LocalDate;
import java.util.List;

import ar.edu.utn.frbb.tup.utils.Banco;
import ar.edu.utn.frbb.tup.utils.Cliente;
import ar.edu.utn.frbb.tup.utils.tipos.TipoPersona;



public class ClienteInputProcessor extends BaseInputProcessor{
    public Cliente ingresarCliente(Banco banco) {

        // Ingreso de datos del Cliente
        Cliente cliente = new Cliente();
        // clearScreen();
        System.out.println("Ingrese el nombre del cliente:");
        String nombre = scanner.nextLine();
        cliente.setNombre(nombre);

        System.out.println("Ingrese el apellido del cliente:");
        String apellido = scanner.nextLine();
        cliente.setApellido(apellido);

        System.out.println("Ingrese el dni del cliente: ");
        long dni = scanner.nextLong ();

        Cliente clienteExistente = banco.existeCliente(dni);

        if (clienteExistente!= null) {
            System.out.println("El cliente ya existe");
            return null;
        }

        cliente.setDni(dni);

        System.out.println("Ingrese el tipo de persona Física(F) o Jurídica(J):");
        String tipoPersonaStr = scanner.nextLine().toUpperCase();
        while (!tipoPersonaStr.equals("F") && !tipoPersonaStr.equals("J")) {
            System.out.println("Tipo de persona inválido. Ingrese NATURAL o JURIDICA:");
            tipoPersonaStr = scanner.nextLine().toUpperCase();
        }
        TipoPersona tipoPersona = TipoPersona.fromString(tipoPersonaStr);
        cliente.setTipoPersona(tipoPersona);

        System.out.println("Ingrese el banco del cliente:");

        String bancoNombre = scanner.nextLine();
        cliente.setBanco(bancoNombre);

        System.out.println("Ingrese la fecha de alta del cliente (Formato: YYYY-MM-DD):");
        LocalDate fechaAlta = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            try {
                fechaAlta = LocalDate.parse(scanner.nextLine());
                fechaValida = true;
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-DD:");
            }
        }
        cliente.setFechaAlta(fechaAlta);

        // clearScreen();
        banco.getClientes().add(cliente);

        return cliente;
    }

    public Cliente EliminarCliente(Banco banco) {
        System.out.println("Ingrese el dni del cliente: ");
        long dni = scanner.nextLong ();
        
        Cliente cliente = banco.existeCliente(dni);
        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return null;
        }

        boolean eliminado = banco.getClientes().remove(cliente);
        if (eliminado) {
            return cliente;
        } else {
            System.out.println("No se pudo eliminar el cliente.");
            return null;
        }
    }

    public Cliente modificarCliente (Banco banco){

        System.out.println("Ingrese el dni del cliente: ");
        long dni = scanner.nextLong ();

        Cliente cliente = banco.existeCliente(dni);

        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return null;
        }

        System.out.println("Ingrese el nombre del cliente:");
        String nombre = scanner.nextLine();
        cliente.setNombre(nombre);

        System.out.println("Ingrese el apellido del cliente:");
        String apellido = scanner.nextLine();
        cliente.setApellido(apellido);

        System.out.println("Ingrese el tipo de persona Física(F) o Jurídica(J):");
        String tipoPersonaStr = scanner.nextLine().toUpperCase();
        while (!tipoPersonaStr.equals("F") && !tipoPersonaStr.equals("J")) {
            System.out.println("Tipo de persona inválido. Ingrese NATURAL o JURIDICA:");
            tipoPersonaStr = scanner.nextLine().toUpperCase();
        }
        TipoPersona tipoPersona = TipoPersona.fromString(tipoPersonaStr);
        cliente.setTipoPersona(tipoPersona);

        System.out.println("Ingrese el banco del cliente:");

        String bancoNombre = scanner.nextLine();
        cliente.setBanco(bancoNombre);

        System.out.println("Ingrese la fecha de alta del cliente (Formato: YYYY-MM-DD):");
        LocalDate fechaAlta = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            try {
                fechaAlta = LocalDate.parse(scanner.nextLine());
                fechaValida = true;
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-DD:");
            }
        }
        cliente.setFechaAlta(fechaAlta);

        int indiceCliente = banco.getClientes().indexOf(cliente);

            // Reemplaza el cliente antiguo con el nuevo cliente modificado
        if (indiceCliente != -1) {
        banco.getClientes().set(indiceCliente, cliente);
        }

        return cliente;
    }

    public List <Cliente> mostrarClientes(Banco banco) {
        return banco.getClientes();
    }
}