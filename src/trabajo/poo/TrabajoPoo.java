package trabajo.poo;

import java.util.Scanner;

public class TrabajoPoo {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        GestorPacientes gestor = new GestorPacientes();

        int opcion;

        do {
            System.out.println("\n===== GESTION DE PACIENTES =====");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Listar pacientes");
            System.out.println("3. Buscar paciente");
            System.out.println("4. Modificar paciente");
            System.out.println("5. Eliminar paciente");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Ingrese DNI: ");
                    String dni = teclado.nextLine();

                    System.out.print("Ingrese nombres: ");
                    String nombres = teclado.nextLine();

                    System.out.print("Ingrese apellidos: ");
                    String apellidos = teclado.nextLine();

                    System.out.print("Ingrese fecha de nacimiento: ");
                    String fechaNacimiento = teclado.nextLine();

                    System.out.print("Ingrese telefono: ");
                    String telefono = teclado.nextLine();

                    Paciente paciente = new Paciente(
                            dni,
                            nombres,
                            apellidos,
                            fechaNacimiento,
                            telefono
                    );

                    gestor.registrarPaciente(paciente);

                    System.out.println("Paciente registrado correctamente.");
                    break;

                case 2:
                    gestor.listarPacientes();
                    break;

                case 3:
                    System.out.print("Ingrese DNI del paciente: ");
                    String dniBuscar = teclado.nextLine();

                    Paciente encontrado = gestor.buscarPaciente(dniBuscar);

                    if (encontrado != null) {
                        System.out.println("Paciente encontrado:");
                        System.out.println("DNI: " + encontrado.getDni());
                        System.out.println("Nombres: " + encontrado.getNombres());
                        System.out.println("Apellidos: " + encontrado.getApellidos());
                        System.out.println("Fecha de nacimiento: " + encontrado.getFechaNacimiento());
                        System.out.println("Telefono: " + encontrado.getTelefono());
                    } else {
                        System.out.println("No se encontró el paciente.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese DNI del paciente a modificar: ");
                    String dniModificar = teclado.nextLine();

                    Paciente pacienteModificar = gestor.buscarPaciente(dniModificar);

                    if (pacienteModificar != null) {

                        System.out.print("Ingrese nuevos nombres: ");
                        String nuevosNombres = teclado.nextLine();

                        System.out.print("Ingrese nuevos apellidos: ");
                        String nuevosApellidos = teclado.nextLine();

                        System.out.print("Ingrese nueva fecha de nacimiento: ");
                        String nuevaFecha = teclado.nextLine();

                        System.out.print("Ingrese nuevo telefono: ");
                        String nuevoTelefono = teclado.nextLine();

                        gestor.modificarPaciente(
                                dniModificar,
                                nuevosNombres,
                                nuevosApellidos,
                                nuevaFecha,
                                nuevoTelefono
                        );

                        System.out.println("Paciente modificado correctamente.");

                    } else {
                        System.out.println("No se encontró el paciente.");
                    }
                    break;

                case 5:
                    System.out.print("Ingrese DNI del paciente a eliminar: ");
                    String dniEliminar = teclado.nextLine();

                    boolean eliminado = gestor.eliminarPaciente(dniEliminar);

                    if (eliminado) {
                        System.out.println("Paciente eliminado correctamente.");
                    } else {
                        System.out.println("No se encontró el paciente.");
                    }
                    break;

                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion no válida.");
            }

        } while (opcion != 6);

        teclado.close();
    }
}